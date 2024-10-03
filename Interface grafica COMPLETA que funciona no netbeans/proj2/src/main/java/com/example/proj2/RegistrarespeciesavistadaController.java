package com.example.proj2;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class RegistrarespeciesavistadaController {

    @FXML
    private ChoiceBox<String> escolherespecies;  // Para selecionar a espécie
    @FXML
    private ChoiceBox<String> escolherareas;     // Para selecionar a área
    @FXML
    private TextField data;                      // Para inserir a data
    @FXML
    private Button confirmar;                    // Botão confirmar

    private Reservaflorestal reserva;

    // Método para inicializar a reserva
    public void setReserva(Reservaflorestal reserva) {
        this.reserva = reserva;
        carregarDados();  // Carregar espécies e áreas nos ChoiceBox
    }

    // Carrega as espécies e áreas nos ChoiceBox
    private void carregarDados() {
        // Carrega os nomes das espécies no ChoiceBox
        List<Especie> especies = reserva.getEspecies();
        escolherespecies.getItems().clear(); // Limpa o ChoiceBox antes de carregar
        for (Especie especie : especies) {
            escolherespecies.getItems().add(especie.getNome());
        }

        // Carrega os nomes das áreas no ChoiceBox
        List<Area> areas = reserva.getAreas();
        escolherareas.getItems().clear(); // Limpa o ChoiceBox antes de carregar
        for (Area area : areas) {
            escolherareas.getItems().add(area.getNome());
        }
    }

    // Ação para registrar o avistamento e voltar ao menu principal
    @FXML
    public void confirmarAvistamento(ActionEvent event) {
        // Captura os dados selecionados
        String nomeEspecie = escolherespecies.getValue();
        String nomeArea = escolherareas.getValue();
        String dataAvistamento = data.getText();  // Captura a data inserida

        // Verifica se todos os campos foram preenchidos
        if (nomeEspecie != null && nomeArea != null && dataAvistamento != null && !dataAvistamento.isEmpty()) {
            // Busca os objetos completos de Especie e Area pelos nomes
            Especie especieSelecionada = buscarEspeciePorNome(nomeEspecie);
            Area areaSelecionada = buscarAreaPorNome(nomeArea);

            if (especieSelecionada != null && areaSelecionada != null) {
                // Registra o avistamento
                reserva.RegistrarAvistamento(areaSelecionada, especieSelecionada, dataAvistamento);
                System.out.println("Avistamento registrado com sucesso!");

                // Limpa os campos após o registro
                escolherespecies.setValue(null);
                escolherareas.setValue(null);
                data.clear();

                // Voltar para o menu inicial
                voltarParaMenuInicial(event);

            } else {
                System.out.println("Erro ao buscar a espécie ou área.");
            }
        } else {
            System.out.println("Por favor, preencha todos os campos.");
        }
    }

    // Função auxiliar para buscar a espécie pelo nome
    private Especie buscarEspeciePorNome(String nomeEspecie) {
        for (Especie especie : reserva.getEspecies()) {
            if (especie.getNome().equals(nomeEspecie)) {
                return especie;
            }
        }
        return null;  // Caso não encontre a espécie
    }

    // Função auxiliar para buscar a área pelo nome
    private Area buscarAreaPorNome(String nomeArea) {
        for (Area area : reserva.getAreas()) {
            if (area.getNome().equals(nomeArea)) {
                return area;
            }
        }
        return null;  // Caso não encontre a área
    }

    // Voltar para o menu inicial
    private void voltarParaMenuInicial(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/layout.fxml"));
            Parent root = loader.load();

            // Obtém o controlador do layout principal e passa a reserva
            LayoutController layoutController = loader.getController();
            layoutController.setReserva(reserva);

            // Atualiza a cena para o layout principal
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
