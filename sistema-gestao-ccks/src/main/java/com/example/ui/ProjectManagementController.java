package com.example.ui;

import com.example.dao.ProjectDAO;
import com.example.dao.UserDAO;
import com.example.model.Project;
import com.example.model.ProjectStatus;
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

/**
 * Controlador para a tela de Gerenciamento de Projetos (ProjectManagement.fxml).
 */
public class ProjectManagementController {

    // --- Componentes da Tabela ---
    @FXML private TableView<Project> projectsTableView;
    @FXML private TableColumn<Project, Integer> idColumn;
    @FXML private TableColumn<Project, String> nameColumn;
    @FXML private TableColumn<Project, String> managerColumn;
    @FXML private TableColumn<Project, ProjectStatus> statusColumn;
    @FXML private TableColumn<Project, LocalDate> endDateColumn;

    // --- Componentes do Formulário ---
    @FXML private TextField nameField;
    @FXML private TextArea descriptionArea;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker plannedEndDatePicker;
    @FXML private ComboBox<ProjectStatus> statusComboBox;
    @FXML private ComboBox<User> managerComboBox;

    // --- Botões e Labels ---
    @FXML private Button newButton;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private Label statusLabel;

    // --- DAOs e Listas ---
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final UserDAO userDAO = new UserDAO();
    private ObservableList<Project> projectList = FXCollections.observableArrayList();
    private Map<Integer, User> userMap;
    private Project selectedProject = null;

    @FXML
    public void initialize() {
        setupTableColumns();

        statusComboBox.getItems().setAll(ProjectStatus.values());

        List<User> allUsers = userDAO.getAllUsers();
        userMap = allUsers.stream().collect(Collectors.toMap(User::getId, user -> user));
        managerComboBox.getItems().setAll(userDAO.getManagerUsers()); // Apenas gerentes no combo

        projectsTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    selectedProject = newSelection;
                    populateForm(newSelection);
                });

        loadProjects();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("plannedEndDate"));

        // Coluna customizada para exibir o nome do gerente
        managerColumn.setCellValueFactory(cellData -> {
            Project project = cellData.getValue();
            User manager = userMap.get(project.getManagerId());
            String managerName = (manager != null) ? manager.getFullName() : "N/A";
            return new SimpleStringProperty(managerName);
        });
    }

    private void loadProjects() {
        projectList.setAll(projectDAO.getAllProjects());
        projectsTableView.setItems(projectList);
    }

    private void populateForm(Project project) {
        if (project != null) {
            nameField.setText(project.getName());
            descriptionArea.setText(project.getDescription());
            startDatePicker.setValue(project.getStartDate());
            plannedEndDatePicker.setValue(project.getPlannedEndDate());
            statusComboBox.setValue(project.getStatus());
            // Encontra o gerente no ComboBox pelo ID
            User manager = userMap.get(project.getManagerId());
            if (manager != null) {
                managerComboBox.setValue(manager);
            } else {
                managerComboBox.getSelectionModel().clearSelection();
            }
        } else {
            clearFormFields();
        }
    }

    @FXML
    void handleNewProjectAction(ActionEvent event) {
        projectsTableView.getSelectionModel().clearSelection();
        selectedProject = null;
        clearFormFields();
        nameField.requestFocus();
        statusLabel.setText("");
    }

    @FXML
    void handleSaveProjectAction(ActionEvent event) {
        if (!isFormValid()) {
            return;
        }

        boolean success;
        String successMessage;
        String errorMessage;

        if (selectedProject == null) { // Modo de Criação
            Project newProject = new Project();
            readForm(newProject);
            success = projectDAO.addProject(newProject);
            successMessage = "Projeto cadastrado com sucesso!";
            errorMessage = "Erro ao cadastrar o projeto.";
        } else { // Modo de Edição
            readForm(selectedProject);
            success = projectDAO.updateProject(selectedProject);
            successMessage = "Projeto atualizado com sucesso!";
            errorMessage = "Erro ao atualizar o projeto.";
        }

        if (success) {
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText(successMessage);
            loadProjects();
            handleNewProjectAction(null);
        } else {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText(errorMessage);
        }
    }

    @FXML
    void handleDeleteProjectAction(ActionEvent event) {
        if (selectedProject == null) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Nenhum projeto selecionado para excluir.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Excluir Projeto: " + selectedProject.getName());
        alert.setContentText("Tem certeza que deseja excluir este projeto? Todas as tarefas associadas também serão excluídas.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = projectDAO.deleteProject(selectedProject.getId());
            if (success) {
                statusLabel.setTextFill(Color.GREEN);
                statusLabel.setText("Projeto excluído com sucesso!");
                loadProjects();
                clearFormFields();
            } else {
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("Erro ao excluir o projeto.");
            }
        }
    }

    private void readForm(Project project) {
        project.setName(nameField.getText().trim());
        project.setDescription(descriptionArea.getText());
        project.setStartDate(startDatePicker.getValue());
        project.setPlannedEndDate(plannedEndDatePicker.getValue());
        project.setStatus(statusComboBox.getValue());
        User selectedManager = managerComboBox.getValue();
        if (selectedManager != null) {
            project.setManagerId(selectedManager.getId());
        }
    }

    private boolean isFormValid() {
        StringBuilder errorMessage = new StringBuilder();
        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            errorMessage.append("O nome do projeto é obrigatório.\n");
        }
        if (startDatePicker.getValue() == null) {
            errorMessage.append("A data de início é obrigatória.\n");
        }
        if (plannedEndDatePicker.getValue() == null) {
            errorMessage.append("A data de término é obrigatória.\n");
        }
        if (statusComboBox.getValue() == null) {
            errorMessage.append("O status é obrigatório.\n");
        }
        if (managerComboBox.getValue() == null) {
            errorMessage.append("O gerente do projeto é obrigatório.\n");
        }
        if (startDatePicker.getValue() != null && plannedEndDatePicker.getValue() != null && plannedEndDatePicker.getValue().isBefore(startDatePicker.getValue())) {
            errorMessage.append("A data de término não pode ser anterior à data de início.\n");
        }
        if (!errorMessage.isEmpty()) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText(errorMessage.toString());
            return false;
        }
        return true;
    }

    private void clearFormFields() {
        nameField.clear();
        descriptionArea.clear();
        startDatePicker.setValue(null);
        plannedEndDatePicker.setValue(null);
        statusComboBox.getSelectionModel().clearSelection();
        managerComboBox.getSelectionModel().clearSelection();
        selectedProject = null;
        statusLabel.setText("");
    }
}