package com.example.ui;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.model.User.UserProfile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Controlador para a tela de Gerenciamento de Usuários (UserManagement.fxml).
 * Lida com a lógica de cadastro de novos usuários.
 */
public class UserManagementController {

    // Injeção dos componentes da interface definidos no FXML.
    @FXML
    private TextField fullNameField;

    @FXML
    private TextField cpfField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField jobTitleField;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<UserProfile> profileComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Label statusLabel; // Para feedback ao usuário (sucesso/erro).

    // Instância do DAO para interagir com o banco de dados.
    private UserDAO userDAO = new UserDAO();

    /**
     * Método de inicialização do controlador.
     * É chamado automaticamente pelo FXMLLoader após os campos @FXML serem injetados.
     * Ideal para configurar o estado inicial dos componentes da UI.
     */
    @FXML
    public void initialize() {
        // Preenche o ComboBox com todos os valores do enum UserProfile.
        // Isso torna o código mais robusto, pois se um novo perfil for adicionado ao enum,
        // ele aparecerá automaticamente na interface.
        profileComboBox.getItems().setAll(UserProfile.values());
    }

    /**
     * Método acionado quando o botão "Salvar" é clicado.
     * Coleta os dados do formulário, valida, e tenta salvar o novo usuário.
     *
     * @param event O evento de ação do clique.
     */
    @FXML
    void handleSaveUserAction(ActionEvent event) {
        // Validação simples para garantir que os campos essenciais não estão vazios.
        if (fullNameField.getText().isEmpty() || loginField.getText().isEmpty() || passwordField.getText().isEmpty() || profileComboBox.getValue() == null) {
            statusLabel.setTextFill(Color.RED); // Define a cor do texto para vermelho.
            statusLabel.setText("Preencha todos os campos obrigatórios (Nome, Login, Senha, Perfil).");
            return; // Interrompe a execução do método se a validação falhar.
        }

        // Cria um novo objeto User e preenche com os dados do formulário.
        User newUser = new User();
        newUser.setFullName(fullNameField.getText());
        newUser.setCpf(cpfField.getText());
        newUser.setEmail(emailField.getText());
        newUser.setJobTitle(jobTitleField.getText());
        newUser.setLogin(loginField.getText());
        newUser.setPassword(passwordField.getText()); // A senha é passada em texto plano; o DAO cuidará da criptografia.
        newUser.setProfile(profileComboBox.getValue());

        // Chama o método do DAO para adicionar o usuário ao banco de dados.
        boolean success = userDAO.addUser(newUser);

        // Fornece feedback ao usuário com base no resultado da operação.
        if (success) {
            statusLabel.setTextFill(Color.GREEN); // Cor verde para sucesso.
            statusLabel.setText("Usuário cadastrado com sucesso!");
            // Limpa os campos do formulário para permitir um novo cadastro.
            clearFormFields();
        } else {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Erro ao cadastrar usuário. Verifique se o login ou CPF já existem.");
        }
    }

    /**
     * Método auxiliar para limpar todos os campos do formulário.
     * Melhora a usabilidade ao permitir que o usuário cadastre várias pessoas em sequência.
     */
    private void clearFormFields() {
        fullNameField.clear();
        cpfField.clear();
        emailField.clear();
        jobTitleField.clear();
        loginField.clear();
        passwordField.clear();
        profileComboBox.getSelectionModel().clearSelection();
    }
}