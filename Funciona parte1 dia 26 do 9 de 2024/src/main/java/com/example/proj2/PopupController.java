package com.example.proj2;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class PopupController {

    @FXML
    private Button Ok;

    @FXML
    public void initialize() {
        // Configura o botão OK para fechar a janela do popup quando clicado
        Ok.setOnAction(event -> {
            try {
                // Fecha o popup
                Stage popupStage = (Stage) Ok.getScene().getWindow();
                popupStage.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar o popup: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
