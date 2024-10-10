package com.example.proj2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class RegistrarareasController {

    @FXML
    private Button confirmar1;
    @FXML
    private TextField nomedaarea;
    @FXML
    private TextField latitude;
    @FXML
    private TextField longitude;
    @FXML
    private TextField tamanhoemhectares;

    private Reservaflorestal reserva;

    public void setReserva(Reservaflorestal reserva) {
        this.reserva = reserva;
    }

    @FXML
    public void confirmar(ActionEvent event) {
        String nome = nomedaarea.getText();
        Double latitudee = Double.parseDouble(latitude.getText());
        Double longitudee = Double.parseDouble(longitude.getText());
        Double tamanhoemhectaress = Double.parseDouble(tamanhoemhectares.getText());

        if (reserva != null) {
            Area novaArea = new Area(nome, latitudee, longitudee, tamanhoemhectaress);
            reserva.RegistrarArea(novaArea);  // Registro da espécie na reserva compartilhada
            
            AreaDAO.inserirArea(novaArea);
            // Limpar os campos após o registro
            nomedaarea.clear();
            latitude.clear();
            longitude.clear();
            tamanhoemhectares.clear();
            
        }

        voltarParaMenuInicial();  // Volta para o menu principal
    }
    
    public void definirLatitudeLongitude(ActionEvent event) {
    try {
        // Carregar o mapa
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("map.fxml"));
        Parent root = fxmlLoader.load();
        
        // Obtém o controlador do mapa para passar as coordenadas de volta
        MapController mapController = fxmlLoader.getController();
        mapController.setRegistrarareasController(this);

        Stage stage = new Stage();
        stage.setTitle("Mapa para Definir Coordenadas");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        }
    }

    // Atualiza os campos de latitude e longitude no formulário
    public void atualizarCoordenadas(double lat, double lon) {
        latitude.setText(String.valueOf(lat));
        longitude.setText(String.valueOf(lon));
    }

    
    private void voltarParaMenuInicial() {
        try {
            // Carrega o layout principal e volta para ele
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/layout.fxml"));
            Parent root = loader.load();

            // Atualiza a cena atual para o layout principal
            Stage stage = (Stage) confirmar1.getScene().getWindow();  
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");

            // Passa a reserva novamente ao LayoutController para manter os dados
            LayoutController layoutController = loader.getController();
            layoutController.setReserva(reserva);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


