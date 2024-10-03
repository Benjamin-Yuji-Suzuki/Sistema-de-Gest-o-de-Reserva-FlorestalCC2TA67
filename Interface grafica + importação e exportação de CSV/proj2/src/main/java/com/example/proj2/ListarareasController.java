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

public class ListarareasController {

    @FXML
    private TableView<Area> areasTable;
    @FXML
    private TableColumn<Area, String> nomeDaAreaColumn;
    @FXML
    private TableColumn<Area, String> latitudeColumn;
    @FXML
    private TableColumn<Area, String> longitudeColumn;
    @FXML
    private TableColumn<Area, String> hectaresColumn;
    @FXML
    private Button confirmar;

    private Reservaflorestal reserva;

    public void setReserva(Reservaflorestal reserva) {
        this.reserva = reserva;
        if (reserva != null) {
            atualizarTableView();
        }
    }

    @FXML
    public void initialize() {
        // Configura as colunas da tabela para associar os dados
        nomeDaAreaColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        hectaresColumn.setCellValueFactory(new PropertyValueFactory<>("hectares"));
    }

    @FXML
    public void atualizarTableView() {
        areasTable.getItems().clear();

        if (reserva != null) {
            List<Area> areas = reserva.getAreas();
            areasTable.getItems().addAll(areas);
        }
    }

    @FXML
    public void confirmar(ActionEvent event) {
        voltarParaMenuInicial();
    }

    private void voltarParaMenuInicial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/layout.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) confirmar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");

            LayoutController layoutController = loader.getController();
            layoutController.setReserva(reserva);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
