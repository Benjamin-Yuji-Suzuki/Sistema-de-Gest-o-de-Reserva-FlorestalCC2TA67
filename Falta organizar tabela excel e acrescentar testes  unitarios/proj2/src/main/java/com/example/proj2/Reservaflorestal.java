package com.example.proj2;

import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.SQLException;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

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

    // Exporta os avistamentos para um arquivo CSV
    public void exportarAvistamentosParaCSV(String caminhoArquivo) throws IOException {
        if (avistamentos == null || avistamentos.isEmpty()) {
            System.out.println("Nenhum avistamento disponível para exportar.");
            return;
        }

        // Usando UTF-8 para lidar com caracteres especiais e CSVWriter configurado corretamente
        try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoArquivo, StandardCharsets.UTF_8))) {
            // Cabeçalho do CSV
            String[] cabecalho = {
                "Nome da Área", "Latitude", "Longitude", "Tamanho (Hectares)",
                "Nome da Espécie", "Tipo", "Está Ameaçada?", "Data do Avistamento"
            };

            // Escreve o cabeçalho no arquivo CSV
            writer.writeNext(cabecalho);

            // Grava os dados dos avistamentos
            for (Avistamento avistamento : avistamentos) {
                Area area = avistamento.getArea();
                Especie especie = avistamento.getEspecieAvistada();

                // Verifica se a área e a espécie não são nulas antes de gravar
                if (area != null && especie != null) {
                    String[] linha = {
                        area.getNome(),
                        String.valueOf(area.getLatitude()),
                        String.valueOf(area.getLongitude()),
                        String.valueOf(area.getHectares()),
                        especie.getNome(),
                        especie.getTipo(),
                        especie.getCondicao(),
                        avistamento.getData()
                    };

                    // Escreve cada linha no CSV
                    writer.writeNext(linha);
                } else {
                    System.out.println("Avistamento inválido: área ou espécie está nula.");
                }
            }

            System.out.println("Dados exportados com sucesso para o arquivo CSV.");
        } catch (IOException e) {
            System.out.println("Erro ao exportar para CSV: " + e.getMessage());
            e.printStackTrace();
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

    public void importarAvistamentosDeCSV(String caminhoArquivo) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Iniciar transação
            conn.setAutoCommit(false);

            try (CSVReader reader = new CSVReader(new FileReader(caminhoArquivo))) {
                String[] linha;
                int linhaCount = 0;
                reader.readNext(); // Pula o cabeçalho

                while ((linha = reader.readNext()) != null) {
                    linhaCount++;
                    try {
                        if (linha.length != 8) {
                            System.out.println("Linha " + linhaCount + " inválida no CSV: " + Arrays.toString(linha));
                            continue; // Ignora linhas malformadas
                        }

                        String nomeArea = linha[0].trim();
                        String latitudeStr = linha[1].trim();
                        String longitudeStr = linha[2].trim();
                        String hectaresStr = linha[3].trim();
                        String nomeEspecie = linha[4].trim();
                        String tipoEspecie = linha[5].trim();
                        String condicaoEspecie = linha[6].trim();
                        String data = linha[7].trim();

                        if (nomeArea.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty() || hectaresStr.isEmpty() ||
                            nomeEspecie.isEmpty() || tipoEspecie.isEmpty() || condicaoEspecie.isEmpty() || data.isEmpty()) {
                            System.out.println("Linha " + linhaCount + " com dados faltando: " + Arrays.toString(linha));
                            continue; // Pula a linha com dados faltantes
                        }

                        Double latitude = Double.parseDouble(latitudeStr);
                        Double longitude = Double.parseDouble(longitudeStr);
                        Double hectares = Double.parseDouble(hectaresStr);

                        // Verificar se a área já existe no banco
                        Area area = AreaDAO.buscarAreaPorNome(nomeArea);
                        if (area == null) {
                            area = new Area(nomeArea, latitude, longitude, hectares);
                            AreaDAO.inserirArea(area);  // Insere a nova área no banco de dados
                            System.out.println("Área inserida: " + nomeArea);
                        }

                        // Verificar se a espécie já existe no banco
                        Especie especie = EspecieDAO.buscarEspeciePorNome(nomeEspecie);
                        if (especie == null) {
                            especie = new Especie(nomeEspecie, tipoEspecie, condicaoEspecie);
                            EspecieDAO.inserirEspecie(especie);  // Insere a nova espécie no banco de dados
                            System.out.println("Espécie inserida: " + nomeEspecie);
                        }

                        // Registra o avistamento
                        Avistamento avistamento = new Avistamento(area, especie, data);
                        AvistamentoDAO.inserirAvistamento(avistamento);
                        System.out.println("Avistamento registrado para espécie: " + nomeEspecie);

                    } catch (NumberFormatException e) {
                        System.out.println("Erro na conversão numérica na linha " + linhaCount + ": " + Arrays.toString(linha));
                    } catch (Exception e) {
                        System.out.println("Erro inesperado na linha " + linhaCount + ": " + Arrays.toString(linha));
                        e.printStackTrace();
                    }
                }

                // Finaliza a transação com sucesso
                conn.commit();
                System.out.println("Importação de avistamentos concluída com sucesso.");
            } catch (Exception e) {
                conn.rollback();  // Desfaz a transação se houver algum erro
                System.out.println("Erro durante a importação, transação desfeita.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("Erro na transação com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
