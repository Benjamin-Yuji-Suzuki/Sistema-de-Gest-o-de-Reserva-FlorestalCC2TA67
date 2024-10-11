package com.example.proj2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    // Método para injetar a reserva no Controller
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
            // Verifica se o limite de áreas foi atingido usando a variável contadora
            if (reserva.getAreas().size() >= 20) {  // Ajuste para o valor limite desejado
                mostrarAlerta("Limite Atingido", "Não é possível adicionar mais áreas. O limite de 20 áreas foi alcançado.");
                voltarParaMenuInicial(); // Redireciona para o menu principal após o alerta
                return; // Sai do método sem registrar a área
            }

            // Cria nova área e registra na reserva
            Area novaArea = new Area(nome, latitudee, longitudee, tamanhoemhectaress);
            reserva.RegistrarArea(novaArea);  // Adiciona na reserva

            // Salva a nova área no banco de dados
            AreaDAO.inserirArea(novaArea);

            // Limpa os campos após o registro
            nomedaarea.clear();
            latitude.clear();
            longitude.clear();
            tamanhoemhectares.clear();
        }

        voltarParaMenuInicial();  // Volta para o menu principal após o registro bem-sucedido
    }

    // Método para abrir o mapa e definir latitude e longitude
    public void definirLatitudeLongitude(ActionEvent event) {
        try {
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

    // Método para voltar ao menu principal
    private void voltarParaMenuInicial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proj2/layout.fxml"));
            Parent root = loader.load();

            // Atualiza a cena atual para o layout principal
            Stage stage = (Stage) confirmar1.getScene().getWindow();  
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");

            // Passa a reserva ao LayoutController para manter os dados
            LayoutController layoutController = loader.getController();
            layoutController.setReserva(reserva);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para mostrar um alerta ao usuário
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
