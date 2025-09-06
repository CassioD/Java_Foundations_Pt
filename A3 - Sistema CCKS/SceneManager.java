package com.seuprojeto;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void loadScene(String fxmlFile, String title) {
        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource("view/" + fxmlFile));
            mainStage.setTitle(title);
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Tratar erro de carregamento de FXML
        }
    }
}