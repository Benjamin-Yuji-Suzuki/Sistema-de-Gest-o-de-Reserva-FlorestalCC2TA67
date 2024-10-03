package com.example.proj2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class ListarespeciesController {

    @FXML
    private TableView<Especie> especiesTable;  // Certifique-se de usar o mesmo nome
    @FXML
    private TableColumn<Especie, String> nomeDaEspecieColumn;
    @FXML
    private TableColumn<Especie, String> tipoColumn;
    @FXML
    private TableColumn<Especie, String> condicaoColumn;
    @FXML
    private Button confirmar;

    // Método chamado quando o FXML é carregado
    @FXML
    public void initialize() {
        // Configura as colunas da tabela para associar os dados
        nomeDaEspecieColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        condicaoColumn.setCellValueFactory(new PropertyValueFactory<>("condicao"));
        
        // Atualiza a lista de espécies ao carregar a tela
        atualizarListaEspecies();
    }

    // Método para buscar as espécies no banco de dados e atualizar a tabela
    public void atualizarListaEspecies() {
        List<Especie> especies = EspecieDAO.buscarTodasEspecies();  // Recarregar do banco de dados
        especiesTable.getItems().setAll(especies);  // Preencher a tabela com os dados
    }

    // Método chamado ao confirmar e voltar para o menu inicial
    @FXML
    public void confirmar(ActionEvent event) {
        voltarParaMenuInicial();
    }

    // Método para voltar ao menu inicial
    private void voltarParaMenuInicial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/layout.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) confirmar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


