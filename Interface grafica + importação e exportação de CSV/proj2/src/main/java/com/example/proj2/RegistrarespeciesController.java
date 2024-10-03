package com.example.proj2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class RegistrarespeciesController {

    @FXML
    private RadioButton nao;
    @FXML
    private RadioButton sim;
    @FXML
    private Button confirmar1;
    @FXML
    private TextField nomedaespecie;
    @FXML
    private TextField tipodaespecie;

    private Reservaflorestal reserva;

    public void setReserva(Reservaflorestal reserva) {
        this.reserva = reserva;
    }

    @FXML
    public void confirmar(ActionEvent event) {
        String nome = nomedaespecie.getText();
        String tipo = tipodaespecie.getText();
        String condicao = sim.isSelected() ? "Sim" : "Não";

        if (reserva != null) {
            Especie novaEspecie = new Especie(nome, tipo, condicao);
            reserva.RegistrarEspecie(novaEspecie);  // Registro da espécie na reserva compartilhada

            // Limpar os campos após o registro
            nomedaespecie.clear();
            tipodaespecie.clear();
            sim.setSelected(false);
            nao.setSelected(false);
        }

        voltarParaMenuInicial();  // Volta para o menu principal
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
