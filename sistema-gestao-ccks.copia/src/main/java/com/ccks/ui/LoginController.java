package com.ccks.ui;

import com.ccks.dao.UserDAO;
import com.ccks.model.User;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador para a tela de Login (Login.fxml).
 * Gerencia a interação do usuário com a interface de login.
 */
public class LoginController {

    // A anotação @FXML injeta os componentes da interface definidos no arquivo FXML
    // que possuem um `fx:id` correspondente.
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label statusLabel; // Usado para exibir mensagens de erro ou status.

    // Instância do DAO para acessar os dados dos usuários.
    private UserDAO userDAO = new UserDAO();

    /**
     * Método acionado quando o botão de login é clicado.
     * O `onAction="#handleLoginButtonAction"` no FXML conecta o clique a este método.
     *
     * @param event O evento de ação gerado pelo clique no botão.
     */
    @FXML
    void handleLoginButtonAction(ActionEvent event) {
        // Obtém o texto dos campos de login e senha.
        String login = loginField.getText();
        String password = passwordField.getText();

        // Chama o método de autenticação do DAO, que verifica as credenciais de forma segura.
        User user = userDAO.authenticate(login, password);

        // Verifica se o DAO retornou um objeto User (o que significa login bem-sucedido).
        if (user != null) {
            // Login bem-sucedido, navega para a tela principal (dashboard).
            try {
                // Carrega o FXML da tela principal.
                Parent mainView = FXMLLoader.load(getClass().getResource("/com/ccks/ui/MainDashboard.fxml"));
                Scene mainScene = new Scene(mainView);

                // Obtém a janela (Stage) atual a partir do evento do botão.
                // Isso permite que a mesma janela seja reutilizada, em vez de abrir uma nova.
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
                // Substitui a cena de login pela cena do painel principal.
                currentStage.setScene(mainScene);
                currentStage.setTitle("Painel Principal - " + user.getProfile()); // Atualiza o título da janela.
                currentStage.centerOnScreen(); // Centraliza a janela na tela.

            } catch (IOException e) {
                // Se o arquivo FXML do dashboard não puder ser carregado, exibe um erro.
                e.printStackTrace();
                statusLabel.setText("Erro ao carregar a tela principal.");
            }
        } else {
            // Se o DAO retornou null, as credenciais estão incorretas.
            statusLabel.setText("Login ou senha inválidos.");
        }
    }
}
