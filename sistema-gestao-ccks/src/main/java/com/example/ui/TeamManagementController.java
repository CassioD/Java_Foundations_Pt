package com.example.ui;

import com.example.dao.TeamDAO;
import com.example.dao.UserDAO;
import com.example.model.Team;
import com.example.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.Collectors;

public class TeamManagementController {

    @FXML private TextField teamNameField;
    @FXML private TextArea teamDescriptionArea;
    @FXML private ListView<User> availableUsersListView;
    @FXML private ListView<User> teamMembersListView;
    @FXML private Label statusLabel;

    private final UserDAO userDAO = new UserDAO();
    private final TeamDAO teamDAO = new TeamDAO();

    private final ObservableList<User> availableUsers = FXCollections.observableArrayList();
    private final ObservableList<User> teamMembers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        availableUsersListView.setItems(availableUsers);
        teamMembersListView.setItems(teamMembers);

        availableUsersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamMembersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        loadAvailableUsers();
    }

    private void loadAvailableUsers() {
        availableUsers.setAll(userDAO.getAllUsers());
    }

    @FXML
    void handleAddMember(ActionEvent event) {
        List<User> selectedUsers = availableUsersListView.getSelectionModel().getSelectedItems();
        if (!selectedUsers.isEmpty()) {
            teamMembers.addAll(selectedUsers);
            availableUsers.removeAll(selectedUsers);
        }
    }

    @FXML
    void handleRemoveMember(ActionEvent event) {
        List<User> selectedUsers = teamMembersListView.getSelectionModel().getSelectedItems();
        if (!selectedUsers.isEmpty()) {
            availableUsers.addAll(selectedUsers);
            teamMembers.removeAll(selectedUsers);
        }
    }

    @FXML
    void handleSaveTeamAction(ActionEvent event) {
        if (teamNameField.getText().isEmpty()) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("O nome da equipe é obrigatório.");
            return;
        }

        Team newTeam = new Team();
        newTeam.setName(teamNameField.getText());
        newTeam.setDescription(teamDescriptionArea.getText());

        List<Integer> memberIds = teamMembers.stream()
                                             .map(User::getId)
                                             .collect(Collectors.toList());

        boolean success = teamDAO.addTeam(newTeam, memberIds);

        if (success) {
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Equipe cadastrada com sucesso!");
            teamNameField.clear();
            teamDescriptionArea.clear();
            teamMembers.clear();
            loadAvailableUsers();
        } else {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Erro ao cadastrar a equipe.");
        }
    }
}