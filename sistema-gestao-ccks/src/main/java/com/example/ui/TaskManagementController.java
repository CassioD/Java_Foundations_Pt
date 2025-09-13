package com.example.ui;

import com.example.dao.ProjectDAO;
import com.example.dao.TaskDAO;
import com.example.dao.UserDAO;
import com.example.model.Project;
import com.example.model.Task;
import com.example.model.TaskStatus;
import com.example.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskManagementController {

    // --- Componentes da Tabela ---
    @FXML private TableView<Task> tasksTableView;
    @FXML private TableColumn<Task, Integer> idColumn;
    @FXML private TableColumn<Task, String> titleColumn;
    @FXML private TableColumn<Task, String> projectColumn;
    @FXML private TableColumn<Task, String> responsibleColumn;
    @FXML private TableColumn<Task, TaskStatus> statusTaskColumn;
    @FXML private TableColumn<Task, LocalDate> endDateColumn;

    // --- Componentes do Formulário ---
    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<Project> projectComboBox;
    @FXML private ComboBox<User> responsibleComboBox;
    @FXML private ComboBox<TaskStatus> statusComboBox;
    @FXML private DatePicker plannedStartDatePicker;
    @FXML private DatePicker plannedEndDatePicker;

    // --- Botões e Labels ---
    @FXML private Button newButton;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private Label statusLabel;

    // --- DAOs e Listas ---
    private final TaskDAO taskDAO = new TaskDAO();
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final UserDAO userDAO = new UserDAO();
    private ObservableList<Task> taskList = FXCollections.observableArrayList();
    private Map<Integer, Project> projectMap;
    private Map<Integer, User> userMap;
    private Task selectedTask = null;

    @FXML
    public void initialize() {
        setupTableColumns();

        List<Project> allProjects = projectDAO.getAllProjects();
        projectMap = allProjects.stream().collect(Collectors.toMap(Project::getId, p -> p));
        projectComboBox.getItems().setAll(allProjects);

        List<User> allUsers = userDAO.getAllUsers();
        userMap = allUsers.stream().collect(Collectors.toMap(User::getId, u -> u));
        responsibleComboBox.getItems().setAll(allUsers);

        statusComboBox.getItems().setAll(TaskStatus.values());

        tasksTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    selectedTask = newSelection;
                    populateForm(newSelection);
                });

        loadTasks();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        statusTaskColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("plannedEndDate"));

        projectColumn.setCellValueFactory(cellData -> {
            Task task = cellData.getValue();
            Project project = projectMap.get(task.getProjectId());
            return new SimpleStringProperty(project != null ? project.getName() : "N/A");
        });

        responsibleColumn.setCellValueFactory(cellData -> {
            Task task = cellData.getValue();
            User user = userMap.get(task.getResponsibleId());
            return new SimpleStringProperty(user != null ? user.getFullName() : "N/A");
        });
    }

    private void loadTasks() {
        taskList.setAll(taskDAO.getAllTasks());
        tasksTableView.setItems(taskList);
    }

    private void populateForm(Task task) {
        if (task != null) {
            titleField.setText(task.getTitle());
            descriptionArea.setText(task.getDescription());
            projectComboBox.setValue(projectMap.get(task.getProjectId()));
            responsibleComboBox.setValue(userMap.get(task.getResponsibleId()));
            statusComboBox.setValue(task.getStatus());
            plannedStartDatePicker.setValue(task.getPlannedStartDate());
            plannedEndDatePicker.setValue(task.getPlannedEndDate());
        } else {
            clearForm();
        }
    }

    @FXML
    void handleNewTaskAction(ActionEvent event) {
        tasksTableView.getSelectionModel().clearSelection();
        selectedTask = null;
        clearForm();
        titleField.requestFocus();
    }

    @FXML
    void handleSaveTaskAction(ActionEvent event) {
        if (!isFormValid()) {
            return;
        }

        boolean success;
        String successMessage;
        String errorMessage;

        if (selectedTask == null) { // Modo de Criação
            Task newTask = new Task();
            readForm(newTask);
            success = taskDAO.addTask(newTask);
            successMessage = "Tarefa cadastrada com sucesso!";
            errorMessage = "Erro ao cadastrar a tarefa.";
        } else { // Modo de Edição
            readForm(selectedTask);
            success = taskDAO.updateTask(selectedTask);
            successMessage = "Tarefa atualizada com sucesso!";
            errorMessage = "Erro ao atualizar a tarefa.";
        }

        if (success) {
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText(successMessage);
            loadTasks();
            handleNewTaskAction(null);
        } else {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText(errorMessage);
        }
    }

    @FXML
    void handleDeleteTaskAction(ActionEvent event) {
        if (selectedTask == null) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Nenhuma tarefa selecionada para excluir.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Excluir Tarefa: " + selectedTask.getTitle());
        alert.setContentText("Tem certeza que deseja excluir esta tarefa?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = taskDAO.deleteTask(selectedTask.getId());
            if (success) {
                statusLabel.setTextFill(Color.GREEN);
                statusLabel.setText("Tarefa excluída com sucesso!");
                loadTasks();
                clearForm();
            } else {
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("Erro ao excluir a tarefa.");
            }
        }
    }

    private void readForm(Task task) {
        task.setTitle(titleField.getText());
        task.setDescription(descriptionArea.getText());
        task.setProjectId(projectComboBox.getValue().getId());
        task.setResponsibleId(responsibleComboBox.getValue().getId());
        task.setStatus(statusComboBox.getValue());
        task.setPlannedStartDate(plannedStartDatePicker.getValue());
        task.setPlannedEndDate(plannedEndDatePicker.getValue());
    }

    private boolean isFormValid() {
        if (titleField.getText().isEmpty() || projectComboBox.getValue() == null ||
            responsibleComboBox.getValue() == null || statusComboBox.getValue() == null ||
            plannedStartDatePicker.getValue() == null || plannedEndDatePicker.getValue() == null) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Preencha todos os campos obrigatórios.");
            return false;
        }
        return true;
    }

    private void clearForm() {
        titleField.clear();
        descriptionArea.clear();
        projectComboBox.getSelectionModel().clearSelection();
        responsibleComboBox.getSelectionModel().clearSelection();
        statusComboBox.getSelectionModel().clearSelection();
        plannedStartDatePicker.setValue(null);
        plannedEndDatePicker.setValue(null);
        selectedTask = null;
        statusLabel.setText("");
    }
}