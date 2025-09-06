package com.example.ui;

import com.example.dao.UserDAO;
import com.example.model.User;
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
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label statusLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    void handleLoginButtonAction(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        User user = userDAO.findByLoginAndPassword(login, password);

        if (user != null) {
            statusLabel.setText("Login bem-sucedido! Bem-vindo, " + user.getFullName());
            // Aqui você navegaria para a próxima tela (dashboard principal)
        } else {
            statusLabel.setText("Login ou senha inválidos.");
        }
    }
}