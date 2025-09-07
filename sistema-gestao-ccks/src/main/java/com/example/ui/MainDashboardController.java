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

    /**
     * Método acionado quando o item de menu "Gerenciar Projetos" é clicado.
     * Abre a janela de cadastro e gerenciamento de projetos.
     *
     * @param event O evento de ação gerado pelo clique no menu.
     */
    @FXML
    void handleProjectManagementAction(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de gerenciamento de projetos.
            URL fxmlUrl = getClass().getResource("/com/example/ui/ProjectManagement.fxml");
            Parent projectManagementView = FXMLLoader.load(fxmlUrl);
            
            // Cria um novo Stage (uma nova janela) para a tela de gerenciamento.
            Stage stage = new Stage();
            stage.setTitle("Gerenciamento de Projetos");
            stage.setScene(new Scene(projectManagementView));
            stage.initModality(Modality.APPLICATION_MODAL); 
            
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    @FXML
    void handleTeamManagementAction(ActionEvent event) {
        openModalWindow("/com/example/ui/TeamManagement.fxml", "Gerenciamento de Equipes");
    }

    @FXML
    void handleProjectTeamAllocationAction(ActionEvent event) {
        openModalWindow("/com/example/ui/ProjectTeamAllocation.fxml", "Alocação de Equipes a Projetos");
    }

    @FXML
    void handleTaskManagementAction(ActionEvent event) {
        openModalWindow("/com/example/ui/TaskManagement.fxml", "Gerenciamento de Tarefas");
    }

    /**
     * Método auxiliar para abrir uma nova janela modal.
     * Refatora a lógica repetitiva de carregar FXML e criar um novo Stage.
     * @param fxmlPath O caminho para o arquivo FXML.
     * @param title O título da nova janela.
     */
    private void openModalWindow(String fxmlPath, String title) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);
            if (fxmlUrl == null) {
                System.err.println("Não foi possível encontrar o arquivo FXML: " + fxmlPath);
                return;
            }
            Parent view = FXMLLoader.load(fxmlUrl);
            
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(view));
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}