package com.example.proj2;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

public class LayoutController {

    private Reservaflorestal reserva = new Reservaflorestal(); // Instância compartilhada

    public void setReserva(Reservaflorestal reserva) {
        this.reserva = reserva;
    }

    @FXML
    void abrirRegistrarEspecie(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/registrarespecies.fxml"));
            Parent root = loader.load();

            RegistrarespeciesController controller = loader.getController();
            controller.setReserva(reserva);  // Passa a reserva compartilhada

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar Espécies");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirListarEspecies(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/listarespecies.fxml"));
            Parent root = loader.load();

            ListarespeciesController controller = loader.getController();
            controller.setReserva(reserva);  // Passa a reserva compartilhada

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Listar Espécies");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Novo método para abrir a tela de registrar áreas
    @FXML
    void abrirRegistrarAreas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/registrarareas.fxml"));
            Parent root = loader.load();

            RegistrarareasController controller = loader.getController();
            controller.setReserva(reserva);  // Passa a reserva compartilhada

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar Áreas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Novo método para abrir a tela de listar áreas
    @FXML
    void abrirListarAreas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/listarareas.fxml"));
            Parent root = loader.load();

            ListarareasController controller = loader.getController();
            controller.setReserva(reserva);  // Passa a reserva compartilhada

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Listar Áreas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void abrirListarEspeciesAvistadas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/listaravistamentodeespecies.fxml"));
            Parent root = loader.load();

            ListaravistamentodeespeciesController controller = loader.getController();
            controller.setReserva(reserva);  // Passa a reserva compartilhada

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Listar Espécies Avistadas");
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    
    @FXML
    void abrirRegistrarEspecieAvistadas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/registrarespeciesavistada.fxml"));
            Parent root = loader.load();

            RegistrarespeciesavistadaController controller = loader.getController();
            controller.setReserva(reserva);  // Passa a reserva compartilhada

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar espécie avistada");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
