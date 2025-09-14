package com.ccks.ui;

import com.ccks.dao.ProjectDAO;
import com.ccks.dao.TeamDAO;
import com.ccks.model.Project;
import com.ccks.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ProjectTeamAllocationController {

    @FXML private ComboBox<Project> projectComboBox;
    @FXML private ComboBox<Team> teamComboBox;
    @FXML private Label statusLabel;

    private final ProjectDAO projectDAO = new ProjectDAO();
    private final TeamDAO teamDAO = new TeamDAO();

    @FXML
    public void initialize() {
        projectComboBox.getItems().setAll(projectDAO.getAllProjects());
        teamComboBox.getItems().setAll(teamDAO.getAllTeams());
    }

    @FXML
    void handleAllocateAction(ActionEvent event) {
        Project selectedProject = projectComboBox.getValue();
        Team selectedTeam = teamComboBox.getValue();

        if (selectedProject == null || selectedTeam == null) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Selecione um projeto e uma equipe.");
            return;
        }

        boolean success = projectDAO.addTeamToProject(selectedProject.getId(), selectedTeam.getId());

        if (success) {
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Equipe '" + selectedTeam.getName() + "' alocada ao projeto '" + selectedProject.getName() + "' com sucesso!");
        } else {
            statusLabel.setTextFill(Color.ORANGE);
            statusLabel.setText("Alocação falhou ou já existe.");
        }
    }
}
