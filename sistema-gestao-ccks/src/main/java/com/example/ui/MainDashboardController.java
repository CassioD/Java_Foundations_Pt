package com.example.ui;

import com.example.dao.ProjectDAO;
import com.example.dao.TeamDAO;
import com.example.dao.TaskDAO;
import com.example.model.Project;
import com.example.model.ProjectStatus;
import com.example.model.User;
import com.example.model.Team;
import com.example.model.Task;
import com.example.model.TaskStatus;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.dao.UserDAO;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controlador para a tela principal (MainDashboard.fxml).
 * Gerencia as ações disponíveis no painel principal, como a navegação para outras telas.
 */
public class MainDashboardController {

    // --- Injeções de componentes do Dashboard ---
    @FXML
    private Label inProgressProjectsLabel;

    @FXML
    private Label pendingTasksLabel;

    @FXML
    private Label overdueTasksLabel;

    @FXML
    private Label totalTeamsLabel;

    @FXML
    private TableView<Task> tasksTableView;

    @FXML
    private TableColumn<Task, String> taskTitleColumn;

    @FXML
    private TableColumn<Task, String> taskProjectColumn;

    @FXML
    private TableColumn<Task, LocalDate> taskDueDateColumn;

    @FXML
    private TableColumn<Task, TaskStatus> taskStatusColumn;

    @FXML
    private TableColumn<Task, String> taskResponsibleColumn;

    @FXML
    private BarChart<String, Number> tasksByProjectChart;

    @FXML
    private CategoryAxis projectAxis;

    @FXML
    private NumberAxis taskCountAxis;

    @FXML
    private PieChart teamsPieChart;

    // --- DAOs para acesso a dados ---
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final TaskDAO taskDAO = new TaskDAO();
    private final TeamDAO teamDAO = new TeamDAO();
    private final UserDAO userDAO = new UserDAO();

    /**
     * Método de inicialização do controlador.
     * É chamado automaticamente pelo FXMLLoader após os campos @FXML serem injetados.
     * Ideal para configurar o estado inicial dos componentes da UI.
     */
    @FXML
    public void initialize() {
        loadDashboardData();
    }

    /**
     * Método acionado quando o item de menu "Gerenciar Usuários" é clicado.
     * O `onAction="#handleUserManagementAction"` no FXML conecta o clique a este método.
     *
     * @param event O evento de ação gerado pelo clique no menu.
     */
    @FXML
    void handleUserManagementAction(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de gerenciamento de usuários.
            URL fxmlUrl = getClass().getResource("/com/example/ui/UserManagement.fxml");
            Parent userManagementView = FXMLLoader.load(fxmlUrl);
            
            // Cria um novo Stage (uma nova janela) para a tela de gerenciamento.
            Stage stage = new Stage();
            stage.setTitle("Gerenciamento de Usuários");
            stage.setScene(new Scene(userManagementView));
            
            // Define a modalidade da janela.
            // Modality.APPLICATION_MODAL bloqueia a interação com a janela principal (dashboard)
            // até que esta nova janela seja fechada. Isso é útil para formulários.
            stage.initModality(Modality.APPLICATION_MODAL); 
            
            // Exibe a janela e espera até que ela seja fechada.
            stage.showAndWait();
            
        } catch (IOException e) {
            // Em caso de erro ao carregar o FXML, imprime o erro para depuração.
            e.printStackTrace(); 
        }
    }

    /**
     * Método acionado quando o item de menu "Gerenciar Projetos" é clicado.
     * Abre a janela de cadastro e gerenciamento de projetos.
     *
     * @param event O evento de ação gerado pelo clique no menu.
     */
    @FXML
    void handleProjectManagementAction(ActionEvent event) {
        try {
            // Carrega o arquivo FXML da tela de gerenciamento de projetos.
            URL fxmlUrl = getClass().getResource("/com/example/ui/ProjectManagement.fxml");
            Parent projectManagementView = FXMLLoader.load(fxmlUrl);
            
            // Cria um novo Stage (uma nova janela) para a tela de gerenciamento.
            Stage stage = new Stage();
            stage.setTitle("Gerenciamento de Projetos");
            stage.setScene(new Scene(projectManagementView));
            stage.initModality(Modality.APPLICATION_MODAL); 
            
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    @FXML
    void handleTeamManagementAction(ActionEvent event) {
        openModalWindow("/com/example/ui/TeamManagement.fxml", "Gerenciamento de Equipes");
    }

    @FXML
    void handleProjectTeamAllocationAction(ActionEvent event) {
        openModalWindow("/com/example/ui/ProjectTeamAllocation.fxml", "Alocação de Equipes a Projetos");
    }

    @FXML
    void handleTaskManagementAction(ActionEvent event) {
        openModalWindow("/com/example/ui/TaskManagement.fxml", "Gerenciamento de Tarefas");
    }

    /**
     * Método auxiliar para abrir uma nova janela modal.
     * Refatora a lógica repetitiva de carregar FXML e criar um novo Stage.
     * @param fxmlPath O caminho para o arquivo FXML.
     * @param title O título da nova janela.
     */
    private void openModalWindow(String fxmlPath, String title) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);
            if (fxmlUrl == null) {
                System.err.println("Não foi possível encontrar o arquivo FXML: " + fxmlPath);
                return;
            }
            Parent view = FXMLLoader.load(fxmlUrl);
            
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(view));
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega os dados dos DAOs e popula os componentes do dashboard.
     */
    private void loadDashboardData() {
        // Carrega todos os projetos e tarefas para os cálculos
        List<Team> allTeams = teamDAO.getAllTeams();
        List<Project> allProjects = projectDAO.getAllProjects();
        List<Task> allTasks = taskDAO.getAllTasks();
        List<User> allUsers = userDAO.getAllUsers();

        // --- Popula os cartões de resumo ---
        long inProgressProjects = allProjects.stream()
                .filter(p -> p.getStatus() == ProjectStatus.EM_ANDAMENTO)
                .count();
        inProgressProjectsLabel.setText(String.valueOf(inProgressProjects));

        long pendingTasks = allTasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.PENDENTE)
                .count();
        pendingTasksLabel.setText(String.valueOf(pendingTasks));

        long overdueTasks = allTasks.stream()
                .filter(t -> t.getStatus() != TaskStatus.CONCLUIDA && t.getPlannedEndDate() != null && t.getPlannedEndDate().isBefore(LocalDate.now()))
                .count();
        overdueTasksLabel.setText(String.valueOf(overdueTasks));

        long totalTeams = allTeams.size();
        totalTeamsLabel.setText(String.valueOf(totalTeams));

        // --- Popula a Tabela de Tarefas ---
        // Cria um mapa de ID do Projeto para Nome do Projeto para consulta rápida
        Map<Integer, String> projectNames = allProjects.stream()
                .collect(Collectors.toMap(Project::getId, Project::getName));

        // Cria um mapa de ID do Usuário para Nome do Usuário para consulta rápida
        Map<Integer, String> userNames = allUsers.stream()
                .collect(Collectors.toMap(User::getId, User::getFullName));

        // Configura as colunas da tabela
        taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskDueDateColumn.setCellValueFactory(new PropertyValueFactory<>("plannedEndDate"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Para o nome do projeto, usamos uma CellValueFactory customizada para buscar o nome a partir do ID
        taskProjectColumn.setCellValueFactory(cellData -> {
            Task task = cellData.getValue();
            String projectName = projectNames.getOrDefault(task.getProjectId(), "Projeto não encontrado");
            return new SimpleStringProperty(projectName);
        });

        // Para o nome do responsável, usamos uma CellValueFactory customizada para buscar o nome a partir do ID
        taskResponsibleColumn.setCellValueFactory(cellData -> {
            Task task = cellData.getValue();
            String responsibleName = userNames.getOrDefault(task.getResponsibleId(), "Não atribuído");
            return new SimpleStringProperty(responsibleName);
        });

        // Carrega as tarefas com prazo próximo ou atrasadas e as exibe na tabela
        List<Task> upcomingTasks = taskDAO.getUpcomingAndOverdueTasks();
        tasksTableView.getItems().setAll(upcomingTasks);

        // --- Popula o Gráfico de Pizza de Equipes ---
        loadTeamsPieChartData(allTeams);
        // --- Popula o Gráfico de Barras de Tarefas por Projeto ---
        loadTasksByProjectChartData(allProjects, allTasks);
    }

    /**
     * Carrega os dados das equipes e popula o gráfico de pizza.
     * @param allTeams A lista de equipes já carregada.
     */
    private void loadTeamsPieChartData(List<Team> allTeams) {
        if (allTeams.isEmpty()) {
            teamsPieChart.setTitle("Nenhuma equipe cadastrada");
            teamsPieChart.setData(FXCollections.observableArrayList());
            return;
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Team team : allTeams) {
            // Para cada equipe, busca o número de membros.
            // Nota: Isso gera uma consulta ao banco para cada equipe.
            // Para um grande número de equipes, otimizar com uma única query seria ideal.
            int memberCount = teamDAO.getTeamMembers(team.getId()).size();
            pieChartData.add(new PieChart.Data(team.getName(), memberCount));
        }

        teamsPieChart.setData(pieChartData);
        teamsPieChart.setTitle("Membros por Equipe");
    }

    /**
     * Carrega os dados de tarefas por projeto e popula o gráfico de barras.
     * @param allProjects A lista de todos os projetos.
     * @param allTasks A lista de todas as tarefas.
     */
    private void loadTasksByProjectChartData(List<Project> allProjects, List<Task> allTasks) {
        // Agrupa as tarefas por ID do projeto e conta quantas tarefas cada projeto tem.
        Map<Integer, Long> tasksPerProject = allTasks.stream()
                .collect(Collectors.groupingBy(Task::getProjectId, Collectors.counting()));

        // Cria a série de dados para o gráfico.
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Nº de Tarefas");

        // Itera sobre todos os projetos para criar uma barra para cada um.
        for (Project project : allProjects) {
            long taskCount = tasksPerProject.getOrDefault(project.getId(), 0L);
            series.getData().add(new XYChart.Data<>(project.getName(), taskCount));
        }

        tasksByProjectChart.getData().clear();
        tasksByProjectChart.getData().add(series);
        tasksByProjectChart.setLegendVisible(false); // A legenda é um pouco redundante aqui.
    }
}