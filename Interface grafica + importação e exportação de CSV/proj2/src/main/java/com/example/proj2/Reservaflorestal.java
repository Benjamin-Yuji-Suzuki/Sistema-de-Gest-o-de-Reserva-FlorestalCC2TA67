package com.example.proj2;

import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Reservaflorestal {

    private int intervaloE = 1;
    private int intervaloA = 1;
    private List<Area> areas = new ArrayList<>();
    private List<Especie> especies = new ArrayList<>();
    private List<Avistamento> avistamentos = new ArrayList<>();

    // Registra uma nova espécie (sem interação do usuário)
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

    // Exibe todas as espécies registradas (pode ser chamado no JavaFX para popular a lista)
    public void MostrarTodasEspecies() {
        if (especies.isEmpty()) {
            System.out.println("Nenhuma espécie registrada");
        } else {
            for (Especie especie : especies) {
                especie.ListarDadosEspecies();  // Use esse método se quiser exibir no console
            }
        }
    }

    // Registra uma nova área (sem interação com o usuário)
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
                local.mostrarAreaDados();  // Novamente, se você quiser exibir no console
            }
        }
    }

    // Registra um avistamento, passando a área, espécie e data diretamente
    public void RegistrarAvistamento(Area area, Especie especie, String dataAvistamento) {
        Avistamento novoAvistamento = new Avistamento(area, especie, dataAvistamento);
        avistamentos.add(novoAvistamento);
        System.out.println("Avistamento registrado com sucesso");
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
    
    public void exportarAvistamentosParaCSV(String caminhoArquivo) throws IOException {
    try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoArquivo))) {
        // Cabeçalho
        String[] cabecalho = {
            "Nome da Área", "Latitude", "Longitude", "Tamanho (Hectares)",
            "Nome da Espécie", "Tipo", "Está Ameaçada?", "Data do Avistamento"
        };
        writer.writeNext(cabecalho);

        // Escreve os avistamentos com todos os detalhes da área e espécie
        for (Avistamento avistamento : avistamentos) {
            Area area = avistamento.getArea();
            Especie especie = avistamento.getEspecieAvistada();

            String[] linha = {
                area.getNome(),
                String.valueOf(area.getLatitude()),
                String.valueOf(area.getLongitude()),
                String.valueOf(area.getHectares()),
                especie.getNome(),
                especie.getTipo(),
                especie.getCondicao(),  // Condição "Sim" ou "Não" para se está ameaçada
                avistamento.getData()
            };
            writer.writeNext(linha);
        }
    }
}


    public Area buscarAreaPorNome(String nome) {
    for (Area area : areas) {
        if (area.getNome().equalsIgnoreCase(nome)) {
            return area;  // Retorna a área se o nome corresponder
        }
    }
    return null;  // Retorna null se não encontrar
}

    public Especie buscarEspeciePorNome(String nome) {
    for (Especie especie : especies) {
        if (especie.getNome().equalsIgnoreCase(nome)) {
            return especie;  // Retorna a espécie se o nome corresponder
        }
    }
    return null;  // Retorna null se não encontrar
}

    
    public void importarAvistamentosDeCSV(String caminhoArquivo) throws IOException, CsvValidationException {
    try (CSVReader reader = new CSVReader(new FileReader(caminhoArquivo))) {
        String[] linha;
        reader.readNext(); // Pula o cabeçalho

        while ((linha = reader.readNext()) != null) {
            String nomeArea = linha[0];
            Double latitude = Double.parseDouble(linha[1]);
            Double longitude = Double.parseDouble(linha[2]);
            Double hectares = Double.parseDouble(linha[3]);
            String nomeEspecie = linha[4];
            String tipoEspecie = linha[5];
            String condicaoEspecie = linha[6];  // Se está ameaçada ou não
            String data = linha[7];

            // Busca ou cria a área com base nos dados importados
            Area area = buscarAreaPorNome(nomeArea);
            if (area == null) {
                area = new Area(nomeArea, latitude, longitude, hectares);
                RegistrarArea(area);  // Registra a área na reserva
            }

            // Busca ou cria a espécie com base nos dados importados
            Especie especie = buscarEspeciePorNome(nomeEspecie);
            if (especie == null) {
                especie = new Especie(nomeEspecie, tipoEspecie, condicaoEspecie);
                RegistrarEspecie(especie);  // Registra a espécie na reserva
            }

            // Registra o avistamento com a área e espécie encontradas ou criadas
            RegistrarAvistamento(area, especie, data);
        }
    }
}
    

    
}
