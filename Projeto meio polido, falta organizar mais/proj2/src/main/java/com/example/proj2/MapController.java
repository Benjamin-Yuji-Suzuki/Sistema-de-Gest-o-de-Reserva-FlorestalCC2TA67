package com.example.proj2;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MapController {

    @FXML
    private WebView mapWebView;

    private RegistrarareasController registrarareasController;

    // Inicializar o mapa
    @FXML
    public void initialize() {
        WebEngine webEngine = mapWebView.getEngine();
        webEngine.setJavaScriptEnabled(true); // Habilitar JavaScript
        webEngine.load(getClass().getResource("map.html").toExternalForm());
    }

    // Define o controlador principal (RegistrarareasController)
    public void setRegistrarareasController(RegistrarareasController controller) {
        this.registrarareasController = controller;
    }

    // Método chamado para confirmar as coordenadas selecionadas no mapa
    @FXML
    public void confirmarCoordenadas() {
        WebEngine webEngine = mapWebView.getEngine();
        String latStr = (String) webEngine.executeScript("localStorage.getItem('selectedLat')");
        String lngStr = (String) webEngine.executeScript("localStorage.getItem('selectedLng')");

        if (latStr != null && lngStr != null) {
            double latitude = Double.parseDouble(latStr);
            double longitude = Double.parseDouble(lngStr);

            // Passa as coordenadas de volta ao controlador principal
            registrarareasController.atualizarCoordenadas(latitude, longitude);

            // Fecha a janela do mapa
            Stage stage = (Stage) mapWebView.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Nenhuma coordenada foi selecionada.");
        }
    }
}
