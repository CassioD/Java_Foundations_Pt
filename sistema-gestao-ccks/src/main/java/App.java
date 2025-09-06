

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carrega a tela de login a partir do FXML
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/ui/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Sistema de Gestão de Projetos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
