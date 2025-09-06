package com.example.ui;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controlador para a tela principal (MainDashboard.fxml).
 * Gerencia as ações disponíveis no painel principal, como a navegação para outras telas.
 */
public class MainDashboardController {

    /**
     * Método acionado quando o item de menu "Gerenciar Usuários" é clicado.
     * O `onAction="#handleUserManagementAction"` no FXML conecta o clique a este método.
     *
     * @param event O evento de ação gerado pelo clique no menu.
     */
    @FXML
    void handleUserManagementAction(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de gerenciamento de usuários.
            URL fxmlUrl = getClass().getResource("/com/example/ui/UserManagement.fxml");
            Parent userManagementView = FXMLLoader.load(fxmlUrl);
            
            // Cria um novo Stage (uma nova janela) para a tela de gerenciamento.
            Stage stage = new Stage();
            stage.setTitle("Gerenciamento de Usuários");
            stage.setScene(new Scene(userManagementView));
            
            // Define a modalidade da janela.
            // Modality.APPLICATION_MODAL bloqueia a interação com a janela principal (dashboard)
            // até que esta nova janela seja fechada. Isso é útil para formulários.
            stage.initModality(Modality.APPLICATION_MODAL); 
            
            // Exibe a janela e espera até que ela seja fechada.
            stage.showAndWait();
            
        } catch (IOException e) {
            // Em caso de erro ao carregar o FXML, imprime o erro para depuração.
            e.printStackTrace(); 
        }
    }
}