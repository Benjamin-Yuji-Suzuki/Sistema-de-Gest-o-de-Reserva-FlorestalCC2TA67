package com.example.proj2;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Reservaflorestal {

    private int intervaloE = 1;
    private int intervaloA = 1;
    private List<Area> areas = new ArrayList<>();
    private List<Especie> especies = new ArrayList<>();
    private List<Avistamento> avistamentos = new ArrayList<>();

    // Registra uma nova espécie
    public void RegistrarEspecie(Especie especie) {
        if (intervaloE >= 200) {
            System.out.println("O limite foi atingido, logo não é capaz de adicionar");
        } else {
            intervaloE++;
            especies.add(especie); // Adiciona a espécie passada como argumento
        }
    }

    // Retorna todas as espécies registradas
    public List<Especie> getEspecies() {
        return especies;
    }

    // Exibe todas as espécies registradas
    public void MostrarTodasEspecies() {
        if (especies.isEmpty()) {
            System.out.println("Nenhuma espécie registrada");
        } else {
            for (Especie especie : especies) {
                especie.ListarDadosEspecies();  // Use esse método para exibir no console
            }
        }
    }

    // Registra uma nova área
    public void RegistrarArea(Area area) {
        if (intervaloA >= 20) {
            System.out.println("O limite foi atingido, logo não é capaz de adicionar");
        } else {
            intervaloA++;
            areas.add(area); // Adiciona a área passada como argumento
        }
    }

    // Retorna todas as áreas registradas
    public List<Area> getAreas() {
        return areas;
    }

    // Mostra todas as áreas registradas
    public void MostrarTodasAreas() {
        if (areas.isEmpty()) {
            System.out.println("Nenhuma área registrada");
        } else {
            for (Area local : areas) {
                local.mostrarAreaDados();  // Exibe os dados da área no console
            }
        }
    }

    // Registra um avistamento
    public void RegistrarAvistamento(Area area, Especie especie, String dataAvistamento) {
        Avistamento novoAvistamento = new Avistamento(area, especie, dataAvistamento);
        avistamentos.add(novoAvistamento);
        System.out.println("Avistamento registrado com sucesso.");
    }

    // Retorna todos os avistamentos registrados
    public List<Avistamento> getAvistamentos() {
        return avistamentos;
    }

    // Mostra todos os avistamentos registrados
    public void MostrarTodosAvistamentos() {
        if (avistamentos.isEmpty()) {
            System.out.println("Nenhum avistamento registrado");
        } else {
            for (Avistamento avistamento : avistamentos) {
                avistamento.mostrarAvistamento();
                System.out.println();
            }
        }
    }
    
    
    private void carregarDadosDoBanco(String dbPath) {
        areas = new ArrayList<>();
        especies = new ArrayList<>();
        avistamentos = new ArrayList<>();

        try (Connection conexao = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stmt = conexao.createStatement()) {

            // Carregar dados das áreas
            ResultSet rsAreas = stmt.executeQuery("SELECT * FROM Area");
            while (rsAreas.next()) {
                Area area = new Area(rsAreas.getString("nome"), rsAreas.getDouble("latitude"),
                                     rsAreas.getDouble("longitude"), rsAreas.getDouble("hectares"));
                areas.add(area);
            }

            // Carregar dados das espécies
            ResultSet rsEspecies = stmt.executeQuery("SELECT * FROM Especie");
            while (rsEspecies.next()) {
                Especie especie = new Especie(rsEspecies.getString("nome"), rsEspecies.getString("tipo"),
                                              rsEspecies.getString("condicao"));
                especies.add(especie);
            }

            // Carregar dados dos avistamentos
            ResultSet rsAvistamentos = stmt.executeQuery("SELECT * FROM Avistamento");
            while (rsAvistamentos.next()) {
                String areaNome = rsAvistamentos.getString("area_nome");
                String especieNome = rsAvistamentos.getString("especie_nome");
                Area area = areas.stream().filter(a -> a.getNome().equals(areaNome)).findFirst().orElse(null);
                Especie especie = especies.stream().filter(e -> e.getNome().equals(especieNome)).findFirst().orElse(null);

                if (area != null && especie != null) {
                    Avistamento avistamento = new Avistamento(area, especie, rsAvistamentos.getString("data"));
                    avistamentos.add(avistamento);
                }
            }

            System.out.println("Dados carregados com sucesso do banco de dados.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
    
    

public void exportarDadosParaCSV(String dbPath, String caminhoArquivo) {
        // Carrega os dados do banco de dados
        carregarDadosDoBanco(dbPath);

        // Verifica se o arquivo já existe para evitar duplicação de cabeçalhos
        boolean arquivoExiste = Files.exists(Paths.get(caminhoArquivo));

        try (Writer fileWriter = new OutputStreamWriter(new FileOutputStream(caminhoArquivo, true), StandardCharsets.UTF_8);
             CSVWriter writer = new CSVWriter(fileWriter, ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            // Se o arquivo não existia antes, adiciona o BOM e os cabeçalhos iniciais
            if (!arquivoExiste) {
                fileWriter.write('\uFEFF'); // Adiciona o BOM para UTF-8
            }

            // Escreve cada seção no CSV, se houver dados a serem exportados
            if (areas != null && !areas.isEmpty()) {
                exportarAreas(writer);  // Exporta as Áreas
            }

            if (especies != null && !especies.isEmpty()) {
                exportarEspecies(writer);  // Exporta as Espécies
            }

            if (avistamentos != null && !avistamentos.isEmpty()) {
                exportarAvistamentos(writer);  // Exporta os Avistamentos
            }

            // Garante que todos os dados são gravados no arquivo
            writer.flush(); // Força a gravação dos dados
            fileWriter.flush(); // Garante que o Writer principal também faça a gravação

            System.out.println("Dados exportados com sucesso para o arquivo CSV.");
        } catch (IOException e) {
            System.out.println("Erro ao exportar para CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para exportar as áreas
    private void exportarAreas(CSVWriter writer) throws IOException {
        if (areas != null && !areas.isEmpty()) {
            String[] cabecalhoAreas = { "Nome da Área", "Latitude", "Longitude", "Tamanho (Hectares)" };
            writer.writeNext(cabecalhoAreas);

            for (Area area : areas) {
                String[] linhaArea = {
                    area.getNome(),
                    "'" + area.getLatitude(), // Adiciona apóstrofo para não formatar como número no Excel
                    "'" + area.getLongitude(),
                    String.valueOf(area.getHectares())
                };
                writer.writeNext(linhaArea);
            }

            writer.writeNext(new String[]{}); // Linha em branco para separar seções
        }
    }

    // Método para exportar as espécies
    private void exportarEspecies(CSVWriter writer) throws IOException {
        if (especies != null && !especies.isEmpty()) {
            String[] cabecalhoEspecies = { "Nome da Espécie", "Tipo", "Está Ameaçada?" };
            writer.writeNext(cabecalhoEspecies);

            for (Especie especie : especies) {
                String[] linhaEspecie = {
                    especie.getNome(),
                    especie.getTipo(),
                    especie.getCondicao()
                };
                writer.writeNext(linhaEspecie);
            }

            writer.writeNext(new String[]{}); // Linha em branco para separar seções
        }
    }

    // Método para exportar os avistamentos
    private void exportarAvistamentos(CSVWriter writer) throws IOException {
        if (avistamentos != null && !avistamentos.isEmpty()) {
            String[] cabecalhoAvistamentos = {
                "Nome da Área", "Latitude", "Longitude", "Tamanho (Hectares)",
                "Nome da Espécie", "Tipo", "Está Ameaçada?", "Data do Avistamento"
            };
            writer.writeNext(cabecalhoAvistamentos);

            for (Avistamento avistamento : avistamentos) {
                Area area = avistamento.getArea();
                Especie especie = avistamento.getEspecieAvistada();

                if (area != null && especie != null) {
                    String[] linhaAvistamento = {
                        area.getNome(),
                        "'" + area.getLatitude(),
                        "'" + area.getLongitude(),
                        String.valueOf(area.getHectares()),
                        especie.getNome(),
                        especie.getTipo(),
                        especie.getCondicao(),
                        avistamento.getData()
                    };
                    writer.writeNext(linhaAvistamento);
                }
            }
        }
    }


    public void carregarAvistamentos() {
        this.avistamentos = AvistamentoDAO.buscarTodosAvistamentos();
        if (this.avistamentos == null || this.avistamentos.isEmpty()) {
            System.out.println("Nenhum avistamento carregado.");
        } else {
            System.out.println("Avistamentos carregados com sucesso: " + this.avistamentos.size() + " avistamentos.");
        }
    }

    // Busca uma área pelo nome
    public Area buscarAreaPorNome(String nome) {
        for (Area area : areas) {
            if (area.getNome().equalsIgnoreCase(nome)) {
                return area;  // Retorna a área se o nome corresponder
            }
        }
        return null;  // Retorna null se não encontrar
    }

    // Busca uma espécie pelo nome
    public Especie buscarEspeciePorNome(String nome) {
        for (Especie especie : especies) {
            if (especie.getNome().equalsIgnoreCase(nome)) {
                return especie;  // Retorna a espécie se o nome corresponder
            }
        }
        return null;  // Retorna null se não encontrar
    }


    
public void importarDadosDeCSV(String dbPath, String csvPath) {
    System.out.println("Iniciando a importação do arquivo CSV: " + csvPath);

    Connection conn = null;
    try {
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        conn.setAutoCommit(false);
        System.out.println("Conectado ao banco de dados SQLite: " + dbPath);

        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(new FileInputStream(csvPath), StandardCharsets.UTF_8))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] linha;
            int linhaCount = 0;

            // Leitura e validação das linhas do CSV
            while ((linha = reader.readNext()) != null) {
                linhaCount++;
                System.out.println("Processando linha " + linhaCount + ": " + Arrays.toString(linha));

                try {
                    // Valida o número de colunas para evitar exceções de conversão
                    if (linha.length != 8) {
                        System.out.println("Linha " + linhaCount + " inválida no CSV: " + Arrays.toString(linha));
                        continue; // Ignora linhas com quantidade errada de colunas
                    }

                    // Extrai os dados das colunas, removendo apóstrofos e espaços extras
                    String nomeArea = linha[0].trim();
                    String latitudeStr = linha[1].replace("'", "").trim();
                    String longitudeStr = linha[2].replace("'", "").trim();
                    String hectaresStr = linha[3].replace("'", "").trim();
                    String nomeEspecie = linha[4].trim();
                    String tipoEspecie = linha[5].trim();
                    String condicaoEspecie = linha[6].trim();
                    String dataAvistamento = linha[7].trim();

                    // Verifica se algum dado está vazio
                    if (nomeArea.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty() || hectaresStr.isEmpty() ||
                        nomeEspecie.isEmpty() || tipoEspecie.isEmpty() || condicaoEspecie.isEmpty() || dataAvistamento.isEmpty()) {
                        System.out.println("Linha " + linhaCount + " com dados faltando: " + Arrays.toString(linha));
                        continue; // Ignora linhas com dados faltando
                    }

                    // Tenta converter para valores numéricos
                    try {
                        Double latitude = Double.parseDouble(latitudeStr);
                        Double longitude = Double.parseDouble(longitudeStr);
                        Double hectares = Double.parseDouble(hectaresStr);
                        System.out.println("Latitude: " + latitude + ", Longitude: " + longitude + ", Hectares: " + hectares);

                        // Verificar se a área já existe no banco de dados
                        Area area = AreaDAO.buscarAreaPorNome(nomeArea);
                        if (area == null) {
                            // Inserir nova área no banco de dados
                            area = new Area(nomeArea, latitude, longitude, hectares);
                            AreaDAO.inserirArea(area);
                            System.out.println("Nova área inserida: " + nomeArea);
                        }

                        // Verificar se a espécie já existe no banco de dados
                        Especie especie = EspecieDAO.buscarEspeciePorNome(nomeEspecie);
                        if (especie == null) {
                            // Inserir nova espécie no banco de dados
                            especie = new Especie(nomeEspecie, tipoEspecie, condicaoEspecie);
                            EspecieDAO.inserirEspecie(especie);
                            System.out.println("Nova espécie inserida: " + nomeEspecie);
                        }

                        // Inserir o avistamento no banco de dados
                        Avistamento avistamento = new Avistamento(area, especie, dataAvistamento);
                        AvistamentoDAO.inserirAvistamento(avistamento);
                        System.out.println("Avistamento registrado para espécie: " + nomeEspecie);

                    } catch (NumberFormatException e) {
                        System.out.println("Erro na conversão numérica na linha " + linhaCount + ": " + Arrays.toString(linha));
                    }

                } catch (Exception e) {
                    System.out.println("Erro inesperado na linha " + linhaCount + ": " + Arrays.toString(linha));
                    e.printStackTrace();
                }
            }

            // Confirma a transação
            conn.commit();
            System.out.println("Importação do CSV concluída com sucesso.");

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Transação desfeita devido a erro.");
                } catch (SQLException rollbackEx) {
                    System.out.println("Erro ao desfazer a transação: " + rollbackEx.getMessage());
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        }

    } catch (SQLException e) {
        System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        e.printStackTrace();
    } finally {
        // Fecha a conexão no finally para garantir que será fechado mesmo em caso de erro
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão com o banco de dados encerrada.");
            } catch (SQLException closeEx) {
                System.out.println("Erro ao fechar a conexão: " + closeEx.getMessage());
                closeEx.printStackTrace();
            }
        }
    }
}



}