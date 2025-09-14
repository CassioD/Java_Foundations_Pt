package com.ccks.ui;

import com.ccks.dao.ProjectDAO;
import com.ccks.dao.TeamDAO;
import com.ccks.dao.TaskDAO;
import com.ccks.model.Project;
import com.ccks.model.ProjectStatus;
import com.ccks.model.User;
import com.ccks.model.Team;
import com.ccks.model.Task;
import com.ccks.model.TaskStatus;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.ccks.dao.UserDAO;
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
    private TableColumn<Task, Void> taskProgressColumn;

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
        openModalWindow("/com/ccks/ui/UserManagement.fxml", "Gerenciamento de Usuários");
    }

    @FXML
    void handleProjectManagementAction(ActionEvent event) {
        openModalWindow("/com/ccks/ui/ProjectManagement.fxml", "Gerenciamento de Projetos");
    }

    @FXML
    void handleTeamManagementAction(ActionEvent event) {
        openModalWindow("/com/ccks/ui/TeamManagement.fxml", "Gerenciamento de Equipes");
    }

    @FXML
    void handleProjectTeamAllocationAction(ActionEvent event) {
        openModalWindow("/com/ccks/ui/ProjectTeamAllocation.fxml", "Alocação de Equipes a Projetos");
    }

    @FXML
    void handleTaskManagementAction(ActionEvent event) {
        openModalWindow("/com/ccks/ui/TaskManagement.fxml", "Gerenciamento de Tarefas");
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
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent view = loader.load();
            
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
        
        long completedTasks = allTasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.CONCLUIDA)
                .count();
        // O fx:id "totalTeamsLabel" foi reutilizado para exibir as tarefas concluídas.
        totalTeamsLabel.setText(String.valueOf(completedTasks));

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

        // Configura a coluna de progresso para exibir uma barra colorida
        taskProgressColumn.setCellFactory(param -> new TableCell<Task, Void>() {
            private final ProgressBar progressBar = new ProgressBar();
            private final VBox container = new VBox(progressBar);

            {
                // Centraliza a barra de progresso na célula
                container.setAlignment(Pos.CENTER);
                progressBar.setMaxWidth(Double.MAX_VALUE);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    Task task = getTableView().getItems().get(getIndex());
                    // Só exibe a barra se a tarefa e suas datas planejadas existirem
                    if (task != null && task.getPlannedStartDate() != null && task.getPlannedEndDate() != null) {
                        LocalDate now = LocalDate.now();
                        LocalDate startDate = task.getPlannedStartDate();
                        LocalDate endDate = task.getPlannedEndDate();

                        double progress = 0.0;
                        String style = "";

                        if (task.getStatus() == TaskStatus.CONCLUIDA) {
                            progress = 1.0;
                            style = "-fx-accent: #27ae60;"; // Verde para concluído
                        } else if (now.isAfter(endDate)) {
                            progress = 1.0;
                            style = "-fx-accent: #e74c3c;"; // Vermelho para atrasado
                        } else if (now.isBefore(startDate)) {
                            progress = 0.0;
                            style = "-fx-accent: #2ecc71;"; // Verde para futuro
                        } else { // Em andamento
                            long totalDuration = endDate.toEpochDay() - startDate.toEpochDay();
                            long elapsedDuration = now.toEpochDay() - startDate.toEpochDay();

                            if (totalDuration > 0) {
                                progress = (double) elapsedDuration / totalDuration;
                            } else { // Tarefa de um dia ou menos
                                progress = 0.5;
                            }
                            progress = Math.max(0, Math.min(progress, 1.0)); // Garante que o progresso está entre 0 e 1
                            style = "-fx-accent: #f1c40f;"; // Amarelo para em andamento
                        }

                        progressBar.setProgress(progress);
                        progressBar.setStyle(style);
                        setGraphic(container);
                    } else {
                        setGraphic(null); // Não mostra a barra se as datas não estiverem definidas
                    }
                }
            }
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
