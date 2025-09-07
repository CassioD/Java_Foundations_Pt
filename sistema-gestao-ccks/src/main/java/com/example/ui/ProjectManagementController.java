package com.example.ui;

import com.example.dao.ProjectDAO;
import com.example.dao.UserDAO;
import com.example.model.Project;
import com.example.model.ProjectStatus;
import com.example.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Controlador para a tela de Gerenciamento de Projetos (ProjectManagement.fxml).
 */
public class ProjectManagementController {

    @FXML private TextField nameField;
    @FXML private TextArea descriptionArea;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker plannedEndDatePicker;
    @FXML private ComboBox<ProjectStatus> statusComboBox;
    @FXML private ComboBox<User> managerComboBox;
    @FXML private Button saveButton;
    @FXML private Label statusLabel;

    private final ProjectDAO projectDAO = new ProjectDAO();
    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        // Popula o ComboBox de status com os valores do Enum
        statusComboBox.getItems().setAll(ProjectStatus.values());

        // Popula o ComboBox de gerentes com usuários que podem gerenciar projetos
        managerComboBox.getItems().setAll(userDAO.getManagerUsers());
    }

    @FXML
    void handleSaveProjectAction(ActionEvent event) {
        // Validação dos campos
        if (nameField.getText().isEmpty() || startDatePicker.getValue() == null || plannedEndDatePicker.getValue() == null || statusComboBox.getValue() == null || managerComboBox.getValue() == null) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Preencha todos os campos obrigatórios.");
            return;
        }

        // Cria o objeto Project com os dados do formulário
        Project newProject = new Project();
        newProject.setName(nameField.getText());
        newProject.setDescription(descriptionArea.getText());
        newProject.setStartDate(startDatePicker.getValue());
        newProject.setPlannedEndDate(plannedEndDatePicker.getValue());
        newProject.setStatus(statusComboBox.getValue());
        newProject.setManagerId(managerComboBox.getValue().getId());

        // Tenta salvar o projeto no banco de dados
        boolean success = projectDAO.addProject(newProject);

        if (success) {
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Projeto cadastrado com sucesso!");
            clearFormFields();
        } else {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Erro ao cadastrar o projeto.");
        }
    }

    private void clearFormFields() {
        nameField.clear();
        descriptionArea.clear();
        startDatePicker.setValue(null);
        plannedEndDatePicker.setValue(null);
        statusComboBox.getSelectionModel().clearSelection();
        managerComboBox.getSelectionModel().clearSelection();
        statusLabel.setText("");
    }
}