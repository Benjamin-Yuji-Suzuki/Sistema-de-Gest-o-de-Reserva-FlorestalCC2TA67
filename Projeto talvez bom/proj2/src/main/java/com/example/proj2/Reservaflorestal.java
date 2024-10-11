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

/**
 * Classe principal para gerenciar uma reserva florestal.
 * Possui métodos para registrar espécies, áreas e avistamentos, além de importar e exportar dados para CSV.
 */
public class Reservaflorestal {

    private List<Area> areas = new ArrayList<>(); // Lista para armazenar as áreas registradas
    private List<Especie> especies = new ArrayList<>(); // Lista para armazenar as espécies registradas
    private List<Avistamento> avistamentos = new ArrayList<>(); // Lista para armazenar os avistamentos registrados

    // --- Métodos para Registro ---

    /**
     * Registra uma nova espécie na lista de espécies, caso o limite não tenha sido atingido.
     *
     * @param especie A espécie a ser registrada.
     */
    public void RegistrarEspecie(Especie especie) {
        if (especies.size() >= 200) {
        System.out.println("O limite foi atingido, logo não é capaz de adicionar");
        } 
        else {
            especies.add(especie);
        }
    }
    

    /**
     * Registra uma nova área na lista de áreas, caso o limite não tenha sido atingido.
     *
     * @param area A área a ser registrada.
     */
    public void RegistrarArea(Area area) {
        if (areas.size() >= 20) {
            System.out.println("O limite foi atingido, logo não é capaz de adicionar");
        } else {            
            areas.add(area); // Adiciona a área passada como argumento
        }
    }

    /**
     * Retorna todas as áreas registradas.
     *
     * @return Lista de áreas registradas.
     */
    public List<Area> getAreas() {
        return areas;
    }
    
    /**
     * Retorna todas as espécies registradas.
     *
     * @return Lista de espécies registradas.
     */
    public List<Especie> getEspecies() {
        return especies;
    }

    /**
     * Registra um avistamento com a área, espécie e data fornecidas.
     *
     * @param area            A área onde o avistamento ocorreu.
     * @param especie         A espécie avistada.
     * @param dataAvistamento A data do avistamento.
     */
    public void RegistrarAvistamento(Area area, Especie especie, String dataAvistamento) {
        Avistamento novoAvistamento = new Avistamento(area, especie, dataAvistamento);
        avistamentos.add(novoAvistamento);
        System.out.println("Avistamento registrado com sucesso.");
    }

    /**
     * Retorna todos os avistamentos registrados.
     *
     * @return Lista de avistamentos registrados.
     */
    public List<Avistamento> getAvistamentos() {
        return avistamentos;
    }

    // --- Métodos para Carregar Dados ---

    /**
     * Carrega os dados de áreas, espécies e avistamentos de um banco de dados SQLite.
     *
     * @param dbPath Caminho do banco de dados SQLite.
     */
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

    // --- Métodos para Exportar e Importar Dados ---

    /**
     * Exporta os dados de áreas, espécies e avistamentos para um arquivo CSV.
     *
     * @param dbPath         Caminho do banco de dados SQLite.
     * @param caminhoArquivo Caminho do arquivo CSV para exportar os dados.
     */
    public void exportarDadosParaCSV(String dbPath, String caminhoArquivo) {
        carregarDadosDoBanco(dbPath); // Carrega os dados do banco de dados

        boolean arquivoExiste = Files.exists(Paths.get(caminhoArquivo)); // Verifica se o arquivo já existe

        try (Writer fileWriter = new OutputStreamWriter(new FileOutputStream(caminhoArquivo, true), StandardCharsets.UTF_8);
             CSVWriter writer = new CSVWriter(fileWriter, ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            if (!arquivoExiste) {
                fileWriter.write('\uFEFF'); // Adiciona o BOM para UTF-8, se o arquivo não existir
            }

            // Exporta as seções (áreas, espécies e avistamentos) conforme existirem dados
            if (!areas.isEmpty()) {
                exportarAreas(writer);
            }
            if (!especies.isEmpty()) {
                exportarEspecies(writer);
            }
            if (!avistamentos.isEmpty()) {
                exportarAvistamentos(writer);
            }

            writer.flush(); // Força a gravação dos dados
            fileWriter.flush(); // Garante que o Writer principal também faça a gravação
            System.out.println("Dados exportados com sucesso para o arquivo CSV.");
        } catch (IOException e) {
            System.out.println("Erro ao exportar para CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exporta as informações de Áreas para o CSV.
     *
     * @param writer CSVWriter a ser utilizado para a escrita no arquivo.
     */
    private void exportarAreas(CSVWriter writer) throws IOException {
        String[] cabecalhoAreas = { "Nome da Área", "Latitude", "Longitude", "Tamanho (Hectares)" };
        writer.writeNext(cabecalhoAreas); // Cabeçalho

        for (Area area : areas) {
            String[] linhaArea = {
                area.getNome(),
                "'" + area.getLatitude(),
                "'" + area.getLongitude(),
                String.valueOf(area.getHectares())
            };
            writer.writeNext(linhaArea); // Escreve os dados da área
        }

        writer.writeNext(new String[]{}); // Linha em branco para separar seções
    }

    /**
     * Exporta as informações de Espécies para o CSV.
     *
     * @param writer CSVWriter a ser utilizado para a escrita no arquivo.
     */
    private void exportarEspecies(CSVWriter writer) throws IOException {
        String[] cabecalhoEspecies = { "Nome da Espécie", "Tipo", "Está Ameaçada?" };
        writer.writeNext(cabecalhoEspecies); // Cabeçalho

        for (Especie especie : especies) {
            String[] linhaEspecie = {
                especie.getNome(),
                especie.getTipo(),
                especie.getCondicao()
            };
            writer.writeNext(linhaEspecie); // Escreve os dados da espécie
        }

        writer.writeNext(new String[]{}); // Linha em branco para separar seções
    }

    /**
     * Exporta as informações de Avistamentos para o CSV.
     *
     * @param writer CSVWriter a ser utilizado para a escrita no arquivo.
     */
    private void exportarAvistamentos(CSVWriter writer) throws IOException {
        String[] cabecalhoAvistamentos = {
            "Nome da Área", "Latitude", "Longitude", "Tamanho (Hectares)",
            "Nome da Espécie", "Tipo", "Está Ameaçada?", "Data do Avistamento"
        };
        writer.writeNext(cabecalhoAvistamentos); // Cabeçalho

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
                writer.writeNext(linhaAvistamento); // Escreve os dados do avistamento
            }
        }
    }

    /**
     * Importa os dados de um arquivo CSV para o banco de dados SQLite.
     *
     * @param dbPath  Caminho do banco de dados SQLite.
     * @param csvPath Caminho do arquivo CSV a ser importado.
     */
    public void importarDadosDeCSV(String dbPath, String csvPath) {
        System.out.println("Iniciando a importação do arquivo CSV: " + csvPath);

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            conn.setAutoCommit(false); // Desabilita auto-commit para transações
            System.out.println("Conectado ao banco de dados SQLite: " + dbPath);

            // Leitura do CSV com opencsv
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

                    // Ignora linhas que não tenham 8 colunas
                    if (linha.length != 8) {
                        System.out.println("Linha " + linhaCount + " inválida no CSV: " + Arrays.toString(linha));
                        continue;
                    }

                    // Extrai e valida os dados das colunas
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
                        continue;
                    }

                    // Tenta converter valores numéricos
                    try {
                        Double latitude = Double.parseDouble(latitudeStr);
                        Double longitude = Double.parseDouble(longitudeStr);
                        Double hectares = Double.parseDouble(hectaresStr);

                        // Verifica se a área já existe
                        Area area = AreaDAO.buscarAreaPorNome(nomeArea);
                        if (area == null) {
                            area = new Area(nomeArea, latitude, longitude, hectares); // Cria nova área
                            AreaDAO.inserirArea(area); // Insere no banco de dados
                        }

                        // Verifica se a espécie já existe
                        Especie especie = EspecieDAO.buscarEspeciePorNome(nomeEspecie);
                        if (especie == null) {
                            especie = new Especie(nomeEspecie, tipoEspecie, condicaoEspecie); // Cria nova espécie
                            EspecieDAO.inserirEspecie(especie); // Insere no banco de dados
                        }

                        // Insere o avistamento no banco de dados
                        Avistamento avistamento = new Avistamento(area, especie, dataAvistamento);
                        AvistamentoDAO.inserirAvistamento(avistamento);
                        System.out.println("Avistamento registrado para a espécie: " + nomeEspecie);

                    } catch (NumberFormatException e) {
                        System.out.println("Erro na conversão numérica na linha " + linhaCount + ": " + Arrays.toString(linha));
                    }
                }

                // Commit da transação
                conn.commit();
                System.out.println("Importação do CSV concluída com sucesso.");

            } catch (Exception e) {
                if (conn != null) {
                    conn.rollback(); // Desfaz transações em caso de erro
                    System.out.println("Transação desfeita devido a erro.");
                }
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fecha a conexão ao banco de dados
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

    // --- Métodos de Busca ---

    /**
     * Busca uma área pelo nome.
     *
     * @param nome O nome da área a ser buscada.
     * @return A área correspondente, ou null se não encontrada.
     */
    public Area buscarAreaPorNome(String nome) {
        for (Area area : areas) {
            if (area.getNome().equalsIgnoreCase(nome)) {
                return area;  // Retorna a área se o nome corresponder
            }
        }
        return null;  // Retorna null se não encontrar
    }

    /**
     * Busca uma espécie pelo nome.
     *
     * @param nome O nome da espécie a ser buscada.
     * @return A espécie correspondente, ou null se não encontrada.
     */
    public Especie buscarEspeciePorNome(String nome) {
        for (Especie especie : especies) {
            if (especie.getNome().equalsIgnoreCase(nome)) {
                return especie;  // Retorna a espécie se o nome corresponder
            }
        }
        return null;  // Retorna null se não encontrar
    }
}