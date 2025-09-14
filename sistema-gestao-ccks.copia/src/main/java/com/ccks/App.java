package com.ccks;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Carrega a tela de login a partir do FXML
            URL fxmlUrl = getClass().getResource("/com/ccks/ui/Login.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Não foi possível encontrar o arquivo FXML. Verifique o caminho: /com/ccks/ui/Login.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Scene scene = new Scene(root);            
            stage.setTitle("Sistema de Gestão de Projetos");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao iniciar a aplicação JavaFX:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
