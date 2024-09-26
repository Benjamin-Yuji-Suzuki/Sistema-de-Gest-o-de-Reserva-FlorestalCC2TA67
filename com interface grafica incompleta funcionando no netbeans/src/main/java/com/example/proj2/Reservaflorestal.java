package com.example.proj2;

import java.util.ArrayList;
import java.util.List;

public class Reservaflorestal {

    private int intervaloE = 1;
    private int intervaloA = 1;
    private List<Area> areas = new ArrayList<>();
    private List<Especie> especies = new ArrayList<>();
    private List<Avistamento> avistamentos = new ArrayList<>();

    // Exibe o nome da reserva (não mais interativo)
    public void nomedaReserva(String nomedaReserva) {
        System.out.println(nomedaReserva);
    }

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
}
