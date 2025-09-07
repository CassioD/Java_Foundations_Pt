package com.example.ui;

import com.example.dao.ProjectDAO;
import com.example.dao.TaskDAO;
import com.example.dao.UserDAO;
import com.example.model.Project;
import com.example.model.Task;
import com.example.model.TaskStatus;
import com.example.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class TaskManagementController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<Project> projectComboBox;
    @FXML private ComboBox<User> responsibleComboBox;
    @FXML private ComboBox<TaskStatus> statusComboBox;
    @FXML private DatePicker plannedStartDatePicker;
    @FXML private DatePicker plannedEndDatePicker;
    @FXML private DatePicker actualStartDatePicker;
    @FXML private DatePicker actualEndDatePicker;
    @FXML private Label statusLabel;

    private final TaskDAO taskDAO = new TaskDAO();
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        projectComboBox.getItems().setAll(projectDAO.getAllProjects());
        responsibleComboBox.getItems().setAll(userDAO.getAllUsers());
        statusComboBox.getItems().setAll(TaskStatus.values());
    }

    @FXML
    void handleSaveTaskAction(ActionEvent event) {
        if (titleField.getText().isEmpty() || projectComboBox.getValue() == null || responsibleComboBox.getValue() == null || statusComboBox.getValue() == null || plannedStartDatePicker.getValue() == null || plannedEndDatePicker.getValue() == null) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Preencha todos os campos obrigatórios.");
            return;
        }

        Task newTask = new Task();
        newTask.setTitle(titleField.getText());
        newTask.setDescription(descriptionArea.getText());
        newTask.setProjectId(projectComboBox.getValue().getId());
        newTask.setResponsibleId(responsibleComboBox.getValue().getId());
        newTask.setStatus(statusComboBox.getValue());
        newTask.setPlannedStartDate(plannedStartDatePicker.getValue());
        newTask.setPlannedEndDate(plannedEndDatePicker.getValue());
        
        // As datas reais são opcionais e podem ser nulas
        newTask.setActualStartDate(actualStartDatePicker.getValue());
        newTask.setActualEndDate(actualEndDatePicker.getValue());

        boolean success = taskDAO.addTask(newTask);

        if (success) {
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Tarefa cadastrada com sucesso!");
            clearForm();
        } else {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Erro ao cadastrar a tarefa.");
        }
    }

    private void clearForm() {
        titleField.clear();
        descriptionArea.clear();
        projectComboBox.getSelectionModel().clearSelection();
        responsibleComboBox.getSelectionModel().clearSelection();
        statusComboBox.getSelectionModel().clearSelection();
        plannedStartDatePicker.setValue(null);
        plannedEndDatePicker.setValue(null);
        actualStartDatePicker.setValue(null);
        actualEndDatePicker.setValue(null);
        statusLabel.setText("");
    }
}