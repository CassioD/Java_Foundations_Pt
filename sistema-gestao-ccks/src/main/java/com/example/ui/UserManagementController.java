package com.example.ui;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.model.User.UserProfile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.util.Optional;

/**
 * Controlador para a tela de Gerenciamento de Usuários (UserManagement.fxml).
 * Lida com a lógica de cadastro, edição e exclusão de usuários.
 */
public class UserManagementController {

    // --- Injeção dos componentes da interface ---
    @FXML private TableView<User> usersTableView;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> fullNameColumn;
    @FXML private TableColumn<User, String> loginColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, UserProfile> profileColumn;

    @FXML private TextField fullNameField;
    @FXML private TextField cpfField;
    @FXML private TextField emailField;
    @FXML private TextField jobTitleField;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<UserProfile> profileComboBox;

    @FXML private Button newButton;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private Label statusLabel;

    private UserDAO userDAO = new UserDAO();
    private ObservableList<User> userList = FXCollections.observableArrayList();
    private User selectedUser = null;

    /**
     * Método de inicialização do controlador.
     */
    @FXML
    public void initialize() {
        // Configura as colunas da tabela
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        profileColumn.setCellValueFactory(new PropertyValueFactory<>("profile"));

        // Preenche o ComboBox com os perfis de usuário
        profileComboBox.getItems().setAll(UserProfile.values());

        // Adiciona um listener para a seleção na tabela
        usersTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedUser = newValue;
                    populateForm(newValue);
                });

        // Carrega os dados iniciais
        loadUsers();
    }

    /**
     * Carrega ou recarrega a lista de usuários do banco de dados e a exibe na tabela.
     */
    private void loadUsers() {
        userList.setAll(userDAO.getAllUsers());
        usersTableView.setItems(userList);
        usersTableView.refresh();
    }

    /**
     * Preenche o formulário com os dados de um usuário selecionado.
     * @param user O usuário cujos dados serão exibidos. Se for nulo, o formulário é limpo.
     */
    private void populateForm(User user) {
        if (user != null) {
            fullNameField.setText(user.getFullName());
            cpfField.setText(user.getCpf());
            emailField.setText(user.getEmail());
            jobTitleField.setText(user.getJobTitle());
            loginField.setText(user.getLogin());
            profileComboBox.setValue(user.getProfile());
            passwordField.clear(); // Senha nunca é exibida
            passwordField.setPromptText("Deixe em branco para não alterar");
        } else {
            clearFormFields();
        }
    }

    /**
     * Limpa o formulário e a seleção, preparando para um novo cadastro.
     */
    @FXML
    void handleNewUserAction(ActionEvent event) {
        usersTableView.getSelectionModel().clearSelection();
        selectedUser = null;
        clearFormFields();
        fullNameField.requestFocus();
        statusLabel.setText("");
    }

    /**
     * Ação do botão Salvar. Cria um novo usuário ou atualiza um existente.
     */
    @FXML
    void handleSaveUserAction(ActionEvent event) {
        if (!isFormValid()) {
            return;
        }

        boolean success;
        String successMessage;
        String errorMessage;

        if (selectedUser == null) { // Modo de Criação
            User newUser = new User();
            readForm(newUser);
            success = userDAO.addUser(newUser);
            successMessage = "Usuário cadastrado com sucesso!";
            errorMessage = "Erro ao cadastrar. Login ou CPF podem já existir.";
        } else { // Modo de Edição
            readForm(selectedUser);
            success = userDAO.updateUser(selectedUser);
            successMessage = "Usuário atualizado com sucesso!";
            errorMessage = "Erro ao atualizar o usuário.";
        }

        if (success) {
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText(successMessage);
            loadUsers();
            handleNewUserAction(null); // Limpa o formulário e a seleção
        } else {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText(errorMessage);
        }
    }

    /**
     * Ação do botão Excluir. Remove o usuário selecionado do sistema.
     */
    @FXML
    void handleDeleteUserAction(ActionEvent event) {
        if (selectedUser == null) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Nenhum usuário selecionado para excluir.");
            return;
        }

        // Exibe um diálogo de confirmação
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Excluir Usuário: " + selectedUser.getFullName());
        alert.setContentText("Tem certeza que deseja excluir este usuário? Esta ação não pode ser desfeita.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = userDAO.deleteUser(selectedUser.getId());
            if (success) {
                statusLabel.setTextFill(Color.GREEN);
                statusLabel.setText("Usuário excluído com sucesso!");
                loadUsers();
                clearFormFields();
            } else {
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("Erro ao excluir. O usuário pode estar associado a projetos ou tarefas.");
            }
        }
    }

    /**
     * Lê os dados do formulário e os popula em um objeto User.
     * @param user O objeto User a ser preenchido.
     */
    private void readForm(User user) {
        user.setFullName(fullNameField.getText());
        user.setCpf(cpfField.getText());
        user.setEmail(emailField.getText());
        user.setJobTitle(jobTitleField.getText());
        user.setLogin(loginField.getText());
        user.setProfile(profileComboBox.getValue());
        
        // A senha só é definida no objeto se o campo foi preenchido.
        // O DAO usará isso para decidir se atualiza a senha no banco.
        if (passwordField.getText() != null && !passwordField.getText().isEmpty()) {
            user.setPassword(passwordField.getText());
        } else {
            user.setPassword(null); // Garante que a senha não será alterada se o campo estiver vazio
        }
    }

    /**
     * Valida os campos do formulário.
     * @return true se o formulário for válido, false caso contrário.
     */
    private boolean isFormValid() {
        String errorMessage = "";
        if (fullNameField.getText() == null || fullNameField.getText().isEmpty()) {
            errorMessage += "Nome completo é obrigatório.\n";
        }
        if (loginField.getText() == null || loginField.getText().isEmpty()) {
            errorMessage += "Login é obrigatório.\n";
        }
        if (profileComboBox.getValue() == null) {
            errorMessage += "Perfil é obrigatório.\n";
        }
        // A senha é obrigatória apenas para novos usuários
        if (selectedUser == null && (passwordField.getText() == null || passwordField.getText().isEmpty())) {
            errorMessage += "Senha é obrigatória para novos usuários.\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText(errorMessage);
            return false;
        }
    }

    /**
     * Método auxiliar para limpar todos os campos do formulário.
     */
    private void clearFormFields() {
        fullNameField.clear();
        cpfField.clear();
        emailField.clear();
        jobTitleField.clear();
        loginField.clear();
        passwordField.clear();
        passwordField.setPromptText("Senha");
        profileComboBox.getSelectionModel().clearSelection();
        selectedUser = null;
    }
}