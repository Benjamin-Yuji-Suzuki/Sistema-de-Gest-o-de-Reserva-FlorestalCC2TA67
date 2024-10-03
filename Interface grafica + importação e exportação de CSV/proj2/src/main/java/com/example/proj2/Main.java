package com.example.proj2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carregar o arquivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        
        // Configurar a cena
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Algum lugar com espécie e área");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public int somar(int a, int b) {
        return a + b;
    }
    
    public static void main(String[] args) {
        launch(args); // Iniciar a aplicação JavaFX
    }
}

