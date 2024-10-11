package com.example.proj2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    // Método para injetar a reserva atual no Controller
    public void setReserva(Reservaflorestal reserva) {
        this.reserva = reserva;
    }

    @FXML
    public void confirmar(ActionEvent event) {
        // Recupera os dados da interface
        String nome = nomedaespecie.getText();
        String tipo = tipodaespecie.getText();
        String condicao = sim.isSelected() ? "Sim" : "Não";

        if (reserva != null) {
            // Verifica se o limite de espécies foi atingido
            if (reserva.getEspecies().size() >= 200) {
                mostrarAlerta("Limite Atingido", "Não é possível adicionar mais espécies. O limite de 200 espécies foi alcançado.");
                voltarParaMenuInicial(); // Volta para o menu principal após exibir o alerta
                return; // Sai do método sem registrar a espécie
            }

            // Cria nova espécie e registra na reserva
            Especie novaEspecie = new Especie(nome, tipo, condicao);
            reserva.RegistrarEspecie(novaEspecie);  // Adiciona na reserva

            // Salva a nova espécie no banco de dados
            EspecieDAO.inserirEspecie(novaEspecie);

            // Limpa os campos após o registro
            nomedaespecie.clear();
            tipodaespecie.clear();
            sim.setSelected(false);
            nao.setSelected(false);
        }

        voltarParaMenuInicial();  // Volta para o menu principal
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
