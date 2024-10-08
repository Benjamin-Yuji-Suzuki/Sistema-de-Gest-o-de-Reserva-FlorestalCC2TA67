package com.example.proj2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import java.io.File;
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
        // Remover esta linha se setReserva não for necessário
        // controller.setReserva(reserva); 

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

        // Não passamos mais a reserva, pois os dados vêm diretamente do banco de dados
        ListaravistamentodeespeciesController controller = loader.getController();

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

        // Não precisamos mais chamar setReserva, pois não usamos mais a reserva no controller
        RegistrarespeciesavistadaController controller = loader.getController();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); 
        stage.setScene(new Scene(root));
        stage.setTitle("Registrar espécie avistada");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    
@FXML
void exportarArquivoCSV(ActionEvent event) {
    try {
        // FileChooser para escolher o local de salvamento
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar arquivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));

        // Obtém o Stage principal para associar ao FileChooser
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                // Definir o caminho do banco de dados SQLite
                String dbPath = "meu_banco.db";  // Caminho relativo se o banco estiver na raiz do projeto
                
                // Exporta os avistamentos passando o caminho do banco e o caminho do arquivo CSV
                reserva.exportarDadosParaCSV(dbPath, file.getAbsolutePath());
                
                System.out.println("Arquivo CSV exportado com sucesso para: " + file.getAbsolutePath());
            } catch (Exception e) {  // Captura qualquer exceção
                System.out.println("Erro ao exportar para CSV: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("A exportação foi cancelada pelo usuário.");
        }
    } catch (Exception e) {
        System.out.println("Erro ao tentar exportar arquivo CSV: " + e.getMessage());
        e.printStackTrace();
    }
}





    
@FXML
void importarArquivoCSV(ActionEvent event) {
    try {
        // FileChooser para escolher o arquivo CSV para importar
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir arquivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));

        // Obtém o Stage principal para associar ao FileChooser
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                // Definir o caminho do banco de dados SQLite
                String dbPath = "meu_banco.db";  // Caminho relativo ou absoluto do banco de dados

                // Chama o método de importação da classe Reservaflorestal
                reserva.importarDadosDeCSV(dbPath, file.getAbsolutePath());
                System.out.println("Arquivo CSV importado com sucesso de: " + file.getAbsolutePath());
            } catch (Exception e) {  // Captura qualquer exceção
                System.out.println("Erro ao importar CSV: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("A importação foi cancelada pelo usuário.");
        }
    } catch (Exception e) {
        System.out.println("Erro ao tentar importar arquivo CSV: " + e.getMessage());
        e.printStackTrace();
    }
}





}


