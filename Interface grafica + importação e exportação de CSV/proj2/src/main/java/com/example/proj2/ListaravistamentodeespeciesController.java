package com.example.proj2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListaravistamentodeespeciesController {

    @FXML
    private ChoiceBox<String> escolherEspecie;  // Para selecionar a espécie
    @FXML
    private TableView<Avistamento> tabelaAvistamentos;  // Tabela de avistamentos
    @FXML
    private TableColumn<Avistamento, String> colunaArea;  // Coluna da área
    @FXML
    private TableColumn<Avistamento, String> colunaData;  // Coluna da data
    @FXML
    private Button botaoVoltar;

    private Reservaflorestal reserva;

    public void setReserva(Reservaflorestal reserva) {
        this.reserva = reserva;
        
        carregarEspecies();
    }
    
    
    private void carregarEspecies() {
    if (reserva != null && reserva.getEspecies() != null) {
        List<Especie> especies = reserva.getEspecies();
        for (Especie especie : especies) {
            escolherEspecie.getItems().add(especie.getNome());
        }
    } else {
        System.out.println("Erro: Reserva ou lista de espécies está nula.");
    }
}
    
    
    
    
    @FXML
    public void initialize() {
        // Configura as colunas da tabela
        colunaArea.setCellValueFactory(new PropertyValueFactory<>("areaNome"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));

        // Adiciona um listener ao ChoiceBox para atualizar os avistamentos quando a espécie for selecionada
        escolherEspecie.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                atualizarTabelaAvistamentos(newSelection);
            }
        });
    }


    // Atualiza a tabela com os avistamentos da espécie selecionada
    private void atualizarTabelaAvistamentos(String nomeEspecie) {
        ObservableList<Avistamento> avistamentosFiltrados = FXCollections.observableArrayList();

        List<Avistamento> avistamentos = reserva.getAvistamentos();
        for (Avistamento avistamento : avistamentos) {
            if (avistamento.getEspecieAvistada().getNome().equals(nomeEspecie)) {
                avistamentosFiltrados.add(avistamento);
            }
        }

        tabelaAvistamentos.setItems(avistamentosFiltrados);
    }

    @FXML
    public void voltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/layout.fxml"));
            Parent root = loader.load();

            LayoutController controller = loader.getController();
            controller.setReserva(reserva);  // Passa a reserva compartilhada

            Stage stage = (Stage) botaoVoltar.getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
