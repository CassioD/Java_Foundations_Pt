package com.example.ui;

import com.example.dao.TeamDAO;
import com.example.dao.UserDAO;
import com.example.model.Team;
import com.example.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeamManagementController {

    // --- Componentes da Tabela de Equipes ---
    @FXML private TableView<Team> teamsTableView;
    @FXML private TableColumn<Team, Integer> teamIdColumn;
    @FXML private TableColumn<Team, String> teamNameColumn;

    // --- Componentes do Formulário ---
    @FXML private TextField teamNameField;
    @FXML private TextArea teamDescriptionArea;
    @FXML private ListView<User> availableUsersListView;
    @FXML private ListView<User> teamMembersListView;

    // --- Botões e Labels ---
    @FXML private Button newButton;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private Label statusLabel;

    // --- DAOs e Listas ---
    private final UserDAO userDAO = new UserDAO();
    private final TeamDAO teamDAO = new TeamDAO();
    private final ObservableList<Team> teamList = FXCollections.observableArrayList();
    private final ObservableList<User> availableUsers = FXCollections.observableArrayList();
    private final ObservableList<User> teamMembers = FXCollections.observableArrayList();
    private Team selectedTeam = null;

    @FXML
    public void initialize() {
        // Configuração da tabela de equipes
        teamIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teamsTableView.setItems(teamList);

        // Configuração das listas de membros
        availableUsersListView.setItems(availableUsers);
        teamMembersListView.setItems(teamMembers);
        availableUsersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamMembersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Listener para seleção na tabela de equipes
        teamsTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    selectedTeam = newSelection;
                    populateForm(newSelection);
                });

        // Carregamento inicial
        loadTeams();
        handleNewTeamAction(null); // Garante que o formulário comece limpo
    }

    private void loadTeams() {
        teamList.setAll(teamDAO.getAllTeams());
    }

    private void populateForm(Team team) {
        if (team != null) {
            teamNameField.setText(team.getName());
            teamDescriptionArea.setText(team.getDescription());

            // Carrega os membros da equipe e os usuários disponíveis
            List<User> allUsers = userDAO.getAllUsers();
            List<User> members = teamDAO.getTeamMembers(team.getId());
            teamMembers.setAll(members);

            // Usuários disponíveis são todos menos os que já são membros
            allUsers.removeAll(members);
            availableUsers.setAll(allUsers);

        } else {
            clearForm();
        }
    }

    @FXML
    void handleAddMember(ActionEvent event) {
        List<User> selectedUsers = availableUsersListView.getSelectionModel().getSelectedItems();
        if (!selectedUsers.isEmpty()) {
            // Adiciona aos membros e remove dos disponíveis
            teamMembers.addAll(selectedUsers);
            availableUsers.removeAll(selectedUsers);
            // Ordena as listas para melhor visualização
            FXCollections.sort(teamMembers, (u1, u2) -> u1.getFullName().compareTo(u2.getFullName()));
            FXCollections.sort(availableUsers, (u1, u2) -> u1.getFullName().compareTo(u2.getFullName()));
        }
    }

    @FXML
    void handleRemoveMember(ActionEvent event) {
        List<User> selectedUsers = teamMembersListView.getSelectionModel().getSelectedItems();
        if (!selectedUsers.isEmpty()) {
            // Adiciona aos disponíveis e remove dos membros
            availableUsers.addAll(selectedUsers);
            teamMembers.removeAll(selectedUsers);
            // Ordena as listas
            FXCollections.sort(teamMembers, (u1, u2) -> u1.getFullName().compareTo(u2.getFullName()));
            FXCollections.sort(availableUsers, (u1, u2) -> u1.getFullName().compareTo(u2.getFullName()));
        }
    }

    @FXML
    void handleNewTeamAction(ActionEvent event) {
        teamsTableView.getSelectionModel().clearSelection();
        selectedTeam = null;
        clearForm();
        teamNameField.requestFocus();
    }

    @FXML
    void handleSaveTeamAction(ActionEvent event) {
        if (teamNameField.getText().isEmpty()) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("O nome da equipe é obrigatório.");
            return;
        }

        List<Integer> memberIds = teamMembers.stream()
                                             .map(User::getId)
                                             .collect(Collectors.toList());
        boolean success;
        String successMessage;
        String errorMessage;

        if (selectedTeam == null) { // Modo de Criação
            Team newTeam = new Team();
            newTeam.setName(teamNameField.getText());
            newTeam.setDescription(teamDescriptionArea.getText());
            success = teamDAO.addTeam(newTeam, memberIds);
            successMessage = "Equipe cadastrada com sucesso!";
            errorMessage = "Erro ao cadastrar a equipe.";
        } else { // Modo de Edição
            selectedTeam.setName(teamNameField.getText());
            selectedTeam.setDescription(teamDescriptionArea.getText());
            success = teamDAO.updateTeam(selectedTeam, memberIds);
            successMessage = "Equipe atualizada com sucesso!";
            errorMessage = "Erro ao atualizar a equipe.";
        }

        if (success) {
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText(successMessage);
            loadTeams();
            handleNewTeamAction(null);
        } else {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText(errorMessage);
        }
    }

    @FXML
    void handleDeleteTeamAction(ActionEvent event) {
        if (selectedTeam == null) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Nenhuma equipe selecionada para excluir.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Excluir Equipe: " + selectedTeam.getName());
        alert.setContentText("Tem certeza que deseja excluir esta equipe? Esta ação não pode ser desfeita.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = teamDAO.deleteTeam(selectedTeam.getId());
            if (success) {
                statusLabel.setTextFill(Color.GREEN);
                statusLabel.setText("Equipe excluída com sucesso!");
                loadTeams();
                handleNewTeamAction(null);
            } else {
                statusLabel.setTextFill(Color.RED);
                statusLabel.setText("Erro ao excluir. A equipe pode estar alocada em um projeto.");
            }
        }
    }

    private void clearForm() {
        teamNameField.clear();
        teamDescriptionArea.clear();
        teamMembers.clear();
        availableUsers.setAll(userDAO.getAllUsers()); // Repopula com todos os usuários
        statusLabel.setText("");
    }
}