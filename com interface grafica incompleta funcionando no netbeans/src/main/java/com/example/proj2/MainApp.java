package com.example.proj2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carregando o arquivo Main.fxml da pasta resources
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/proj2/layout.fxml"));
        
        // Definindo o título da janela
        primaryStage.setTitle("Sistema de Registro de Espécies");

        // Definindo a cena com o conteúdo do arquivo FXML
        primaryStage.setScene(new Scene(root));
        
        // Exibindo a janela
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Método principal que inicia o aplicativo JavaFX
        launch(args);
    }
}
