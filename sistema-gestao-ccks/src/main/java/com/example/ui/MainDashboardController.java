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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.dao.UserDAO;
import javafx.scene.paint.Color;
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
    private TableColumn<Task, String> taskDeadlineStatusColumn;

    @FXML
    private TableColumn<Task, TaskStatus> taskStatusColumn;

    @FXML
    private TableColumn<Task, String> taskResponsibleColumn;

    @FXML
    private TableColumn<Task, Void> taskProgressColumn;

    @FXML
    private StackedBarChart<String, Number> tasksByProjectChart;

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
        // Configurações visuais para limpar o gráfico de barras
        tasksByProjectChart.setAnimated(false);
        tasksByProjectChart.setVerticalGridLinesVisible(false);
        tasksByProjectChart.setHorizontalGridLinesVisible(false);

        // Adiciona um padding no topo da área de plotagem para dar espaço aos rótulos de total.
        // O lookup é usado aqui porque o estilo precisa ser aplicado após a UI ser construída.
        Platform.runLater(() -> {
            Node plotContent = tasksByProjectChart.lookup(".plot-content");
            if (plotContent != null) {
                plotContent.setStyle("-fx-padding: 20px 5px 5px 5px;");
            }
        });
        loadDashboardData();
    }

    /**
     * Método acionado pelo botão "Atualizar Dashboard".
     * Recarrega todos os dados do dashboard para refletir quaisquer alterações
     * feitas no banco de dados.
     * @param event O evento de ação gerado pelo clique no botão.
     */
    @FXML
    void handleRefreshAction(ActionEvent event) {
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
        openModalWindow("/com/example/ui/UserManagement.fxml", "Gerenciamento de Usuários");
    }

    @FXML
    void handleProjectManagementAction(ActionEvent event) {
        openModalWindow("/com/example/ui/ProjectManagement.fxml", "Gerenciamento de Projetos");
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
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent view = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(view));
            stage.initModality(Modality.APPLICATION_MODAL);
            
            // O método showAndWait() bloqueia a execução até que a janela modal seja fechada.
            stage.showAndWait();

            // Após o fechamento da janela modal, os dados do dashboard são recarregados automaticamente.
            loadDashboardData();
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

        // Centraliza o conteúdo das colunas da tabela de tarefas
        taskTitleColumn.setStyle("-fx-alignment: CENTER;");
        taskProjectColumn.setStyle("-fx-alignment: CENTER;");
        taskResponsibleColumn.setStyle("-fx-alignment: CENTER;");
        taskDueDateColumn.setStyle("-fx-alignment: CENTER;");
        taskDeadlineStatusColumn.setStyle("-fx-alignment: CENTER;");
        taskStatusColumn.setStyle("-fx-alignment: CENTER;");

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

        // Coluna customizada para exibir a situação do prazo (Atraso/No prazo)
        taskDeadlineStatusColumn.setCellFactory(column -> new TableCell<Task, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                // Limpa a célula se estiver vazia ou se não houver tarefa na linha
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                    setStyle("");
                } else {
                    Task task = getTableRow().getItem();
                    LocalDate dueDate = task.getPlannedEndDate();

                    // A lógica só se aplica a tarefas não concluídas que possuem um prazo definido
                    if (task.getStatus() != TaskStatus.CONCLUIDA && dueDate != null) {
                        if (dueDate.isBefore(LocalDate.now())) {
                            setText("Atraso");
                            setTextFill(javafx.scene.paint.Color.RED);
                        } else {
                            setText("No prazo");
                            setTextFill(javafx.scene.paint.Color.DARKGREEN);
                        }
                    } else {
                        setText(""); // Não exibe nada para tarefas concluídas ou sem prazo
                    }
                }
            }
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

                        // A lógica de cores agora prioriza o STATUS da tarefa.
                        if (task.getStatus() == TaskStatus.CONCLUIDA) {
                            progress = 1.0;
                            style = "-fx-accent: #27ae60;"; // Verde para concluído
                        } else if (task.getStatus() == TaskStatus.EM_EXECUCAO) {
                            // Se está em execução, a cor é verde, e o progresso é calculado com base no tempo.
                            long totalDuration = endDate.toEpochDay() - startDate.toEpochDay();
                            long elapsedDuration = now.toEpochDay() - startDate.toEpochDay();

                            if (totalDuration > 0) {
                                progress = (double) elapsedDuration / totalDuration;
                            } else { // Tarefa de um dia ou menos
                                progress = 0.5;
                            }
                            progress = Math.max(0, Math.min(progress, 1.0));
                            style = "-fx-accent: #2ecc71;"; // Verde para futuro
                        } else if (task.getStatus() == TaskStatus.PENDENTE) {
                            // Para tarefas pendentes, a cor depende da data.
                            if (now.isAfter(endDate)) {
                                progress = 1.0;
                                style = "-fx-accent: #e74c3c;"; // Vermelho para atrasado
                            } else if (now.isBefore(startDate)) {
                                progress = 0.0;
                                style = "-fx-accent: #bdc3c7;"; // Cinza para futuro/pendente
                            } else { // Pendente e no prazo
                                long totalDuration = endDate.toEpochDay() - startDate.toEpochDay();
                                long elapsedDuration = now.toEpochDay() - startDate.toEpochDay();
                                if (totalDuration > 0) {
                                    progress = (double) elapsedDuration / totalDuration;
                                } else {
                                    progress = 0.5;
                                }
                                progress = Math.max(0, Math.min(progress, 1.0));
                                style = "-fx-accent: #f1c40f;"; // Amarelo para pendente
                            }
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
            // Formata o rótulo para incluir o nome da equipe e a contagem de membros.
            String label = String.format("%s (%d)", team.getName(), memberCount);
            pieChartData.add(new PieChart.Data(label, memberCount));
        }

        teamsPieChart.setData(pieChartData);
        teamsPieChart.setTitle("Membros por Equipe");
        teamsPieChart.setLegendVisible(false); // Remove a legenda da parte inferior do gráfico
    }

    /**
     * Carrega os dados de tarefas por projeto e popula o gráfico de barras.
     * @param allProjects A lista de todos os projetos.
     * @param allTasks A lista de todas as tarefas.
     */
    private void loadTasksByProjectChartData(List<Project> allProjects, List<Task> allTasks) {
        // Define as séries para cada status de tarefa
        XYChart.Series<String, Number> pendingSeries = new XYChart.Series<>();
        pendingSeries.setName("Pendente");

        XYChart.Series<String, Number> inProgressSeries = new XYChart.Series<>();
        inProgressSeries.setName("Em Execução");

        XYChart.Series<String, Number> completedSeries = new XYChart.Series<>();
        completedSeries.setName("Concluída");

        // Agrupa as tarefas por projeto e, em seguida, por status, contando cada grupo.
        Map<Integer, Map<TaskStatus, Long>> tasksByProjectAndStatus = allTasks.stream()
                .collect(Collectors.groupingBy(
                    Task::getProjectId,
                    Collectors.groupingBy(Task::getStatus, Collectors.counting())
                ));

        long maxTasksInSingleProject = 0;

        // Itera sobre todos os projetos para popular as séries
        for (Project project : allProjects) {
            Map<TaskStatus, Long> statusCounts = tasksByProjectAndStatus.getOrDefault(project.getId(), Collections.emptyMap());

            long pendingCount = statusCounts.getOrDefault(TaskStatus.PENDENTE, 0L);
            long inProgressCount = statusCounts.getOrDefault(TaskStatus.EM_EXECUCAO, 0L);
            long completedCount = statusCounts.getOrDefault(TaskStatus.CONCLUIDA, 0L);

            // Calcula o total de tarefas do projeto para ajustar a escala do eixo Y
            long totalForProject = pendingCount + inProgressCount + completedCount;
            if (totalForProject > maxTasksInSingleProject) {
                maxTasksInSingleProject = totalForProject;
            }

            XYChart.Data<String, Number> pendingData = new XYChart.Data<>(project.getName(), pendingCount);
            pendingData.setNode(createLabelNodeForBar(pendingCount));
            pendingSeries.getData().add(pendingData);

            XYChart.Data<String, Number> inProgressData = new XYChart.Data<>(project.getName(), inProgressCount);
            inProgressData.setNode(createLabelNodeForBar(inProgressCount));
            inProgressSeries.getData().add(inProgressData);

            XYChart.Data<String, Number> completedData = new XYChart.Data<>(project.getName(), completedCount);
            completedData.setNode(createTotalLabelNode(completedCount, totalForProject));
            completedSeries.getData().add(completedData);
        }

        // Configura o eixo Y para ter uma escala apropriada, evitando que fique muito grande
        taskCountAxis.setAutoRanging(false); // Desativa o dimensionamento automático para ter controle total
        taskCountAxis.setLowerBound(0);

        // Define o limite superior com uma pequena margem. Se não houver tarefas, define um limite mínimo.
        double upperBound = maxTasksInSingleProject == 0 ? 5.0 : maxTasksInSingleProject + 2.0;
        taskCountAxis.setUpperBound(upperBound);

        // Define a unidade do tick para ser um inteiro e evitar muitos marcadores no eixo.
        double tickUnit = Math.max(1.0, Math.ceil(upperBound / 10.0)); // Tenta ter no máximo 10 marcadores
        taskCountAxis.setTickUnit(tickUnit);

        tasksByProjectChart.getData().clear();
        tasksByProjectChart.getData().addAll(pendingSeries, inProgressSeries, completedSeries);
        tasksByProjectChart.setLegendVisible(true); // Agora a legenda é útil

        // Oculta o eixo Y (números e linha) para evitar redundância com os rótulos nas barras.
        taskCountAxis.setTickLabelsVisible(false);
        taskCountAxis.setOpacity(0);
    }

    /**
     * Cria um nó (StackPane) com um rótulo para ser exibido dentro de um segmento da barra.
     * @param value O valor a ser exibido no rótulo.
     * @return Um nó StackPane contendo o rótulo, ou null se o valor for zero.
     */
    private Node createLabelNodeForBar(long value) {
        if (value == 0) {
            return null; // Não cria rótulo para valores zero.
        }
        StackPane node = new StackPane();
        Label label = new Label(String.valueOf(value));
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-font-size: 9px; -fx-font-weight: bold;");
        node.getChildren().add(label);
        return node;
    }

    /**
     * Cria um nó (StackPane) para o segmento superior da barra, que inclui tanto o rótulo
     * do segmento quanto o rótulo do total, posicionado acima da barra.
     * @param segmentValue O valor do segmento superior.
     * @param totalValue O valor total da barra.
     * @return Um nó StackPane com os rótulos, ou null se o total for zero.
     */
    private Node createTotalLabelNode(long segmentValue, long totalValue) {
        if (totalValue == 0) {
            return null;
        }

        StackPane node = new StackPane();

        // Adiciona o rótulo para o segmento, se não for zero
        if (segmentValue > 0) {
            Label segmentLabel = new Label(String.valueOf(segmentValue));
            segmentLabel.setTextFill(Color.WHITE);
            segmentLabel.setStyle("-fx-font-size: 9px; -fx-font-weight: bold;");
            node.getChildren().add(segmentLabel);
        }

        // Adiciona o rótulo para o total, posicionado acima da barra
        Label totalLabel = new Label(String.valueOf(totalValue));
        totalLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #333;");
        StackPane.setAlignment(totalLabel, Pos.TOP_CENTER);
        totalLabel.setTranslateY(-15); // Move o rótulo para cima
        node.getChildren().add(totalLabel);

        return node;
    }
}