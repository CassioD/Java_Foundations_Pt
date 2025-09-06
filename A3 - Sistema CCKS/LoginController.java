package com.seuprojeto.controller;

import com.seuprojeto.dao.UsuarioDAO;
import com.seuprojeto.SceneManager;
import com.seuprojeto.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button loginButton;

    @FXML
    private Label statusLabel;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    void handleLogin(ActionEvent event) {
        String login = loginField.getText();
        String senha = senhaField.getText();

        Usuario usuario = usuarioDAO.autenticar(login, senha);

        if (usuario != null) {
            // Login bem-sucedido, navega para a tela principal
            // TODO: Crie o arquivo MainScreen.fxml
            // SceneManager.loadScene("MainScreen.fxml", "Painel Principal");
            statusLabel.setText("Login bem-sucedido! Navegando..."); // Feedback temporário
        } else {
            senhaField.clear(); // Limpa o campo de senha por segurança
            statusLabel.setText("Login ou senha inválidos.");
        }
    }
}