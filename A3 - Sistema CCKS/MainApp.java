package com.seuprojeto;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Configura o palco principal no nosso gerenciador de cenas
        SceneManager.setStage(primaryStage);
        // Carrega a primeira cena (Login)
        SceneManager.loadScene("Login.fxml", "Login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}