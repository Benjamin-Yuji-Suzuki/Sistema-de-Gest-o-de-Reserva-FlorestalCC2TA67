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

    @FXML
    public void initialize() {
        carregarDados();  // Carregar espécies e áreas nos ChoiceBox ao inicializar
    }

    // Carrega as espécies e áreas nos ChoiceBox
    private void carregarDados() {
        // Carrega as espécies do banco de dados para o ChoiceBox
        escolherespecies.getItems().clear(); // Limpa antes de carregar
        List<Especie> especies = EspecieDAO.buscarTodasEspecies();  // Busca diretamente do banco de dados
        if (especies != null) {
            for (Especie especie : especies) {
                escolherespecies.getItems().add(especie.getNome());
            }
        }

        // Carrega as áreas do banco de dados para o ChoiceBox
        escolherareas.getItems().clear(); // Limpa antes de carregar
        List<Area> areas = AreaDAO.buscarTodasAreas();  // Busca diretamente do banco de dados
        if (areas != null) {
            for (Area area : areas) {
                escolherareas.getItems().add(area.getNome());
            }
        }
    }

    @FXML
    public void confirmarAvistamento(ActionEvent event) {
        // Captura os dados selecionados
        String nomeEspecie = escolherespecies.getValue();
        String nomeArea = escolherareas.getValue();
        String dataAvistamento = data.getText();  // Captura a data inserida

        // Verifica se todos os campos foram preenchidos
        if (nomeEspecie != null && nomeArea != null && dataAvistamento != null && !dataAvistamento.isEmpty()) {
            // Busca os objetos completos de Especie e Area pelos nomes diretamente do DAO
            Especie especieSelecionada = EspecieDAO.buscarEspeciePorNome(nomeEspecie);
            Area areaSelecionada = AreaDAO.buscarAreaPorNome(nomeArea);

            if (especieSelecionada != null && areaSelecionada != null) {
                // Cria o objeto Avistamento
                Avistamento novoAvistamento = new Avistamento(areaSelecionada, especieSelecionada, dataAvistamento);
                
                // Registra o avistamento no banco de dados
                AvistamentoDAO.inserirAvistamento(novoAvistamento);
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

    // Voltar para o menu inicial
    private void voltarParaMenuInicial(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/layout.fxml"));
            Parent root = loader.load();

            // Atualiza a cena para o layout principal
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


