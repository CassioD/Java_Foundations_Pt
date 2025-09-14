package com.ccks.dao;

import com.ccks.model.Task;
import com.ccks.model.TaskStatus;
import com.ccks.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Task.
 * Encapsula a lógica de acesso ao banco de dados para as tarefas.
 */
public class TaskDAO {

    /**
     * Busca todas as tarefas cadastradas no banco de dados.
     * @return Uma lista com todos os objetos Task.
     */
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks ORDER BY planned_end_date";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                tasks.add(mapRowToTask(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Busca tarefas que não estão concluídas e cujo prazo final está próximo (próximos 7 dias) ou já passou.
     * @return Uma lista de tarefas filtradas e ordenadas por prazo.
     */
    public List<Task> getUpcomingAndOverdueTasks() {
        List<Task> tasks = new ArrayList<>();
        // Busca tarefas que não estão 'CONCLUIDA' e cujo prazo é hoje, no passado, ou nos próximos 7 dias.
        String sql = "SELECT * FROM tasks WHERE status != 'CONCLUIDA' AND planned_end_date <= CURDATE() + INTERVAL 7 DAY ORDER BY planned_end_date ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                tasks.add(mapRowToTask(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Adiciona uma nova tarefa ao banco de dados.
     * @param task O objeto Task a ser salvo.
     * @return true se a inserção for bem-sucedida, false caso contrário.
     */
    public boolean addTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, project_id, responsible_id, status, planned_start_date, planned_end_date, actual_start_date, actual_end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setInt(3, task.getProjectId());
            pstmt.setInt(4, task.getResponsibleId());
            pstmt.setString(5, task.getStatus().name());

            // Converte LocalDate para java.sql.Date, tratando nulos
            if (task.getPlannedStartDate() != null) {
                pstmt.setDate(6, Date.valueOf(task.getPlannedStartDate()));
            } else {
                pstmt.setNull(6, java.sql.Types.DATE);
            }
            if (task.getPlannedEndDate() != null) {
                pstmt.setDate(7, Date.valueOf(task.getPlannedEndDate()));
            } else {
                pstmt.setNull(7, java.sql.Types.DATE);
            }
            if (task.getActualStartDate() != null) {
                pstmt.setDate(8, Date.valueOf(task.getActualStartDate()));
            } else {
                pstmt.setNull(8, java.sql.Types.DATE);
            }
            if (task.getActualEndDate() != null) {
                pstmt.setDate(9, Date.valueOf(task.getActualEndDate()));
            } else {
                pstmt.setNull(9, java.sql.Types.DATE);
            }

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Atualiza uma tarefa existente no banco de dados.
     * @param task O objeto Task com os dados atualizados.
     * @return true se a atualização for bem-sucedida, false caso contrário.
     */
    public boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, project_id = ?, responsible_id = ?, status = ?, " +
                     "planned_start_date = ?, planned_end_date = ?, actual_start_date = ?, actual_end_date = ? " +
                     "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setInt(3, task.getProjectId());
            pstmt.setInt(4, task.getResponsibleId());
            pstmt.setString(5, task.getStatus().name());

            pstmt.setDate(6, task.getPlannedStartDate() != null ? Date.valueOf(task.getPlannedStartDate()) : null);
            pstmt.setDate(7, task.getPlannedEndDate() != null ? Date.valueOf(task.getPlannedEndDate()) : null);
            pstmt.setDate(8, task.getActualStartDate() != null ? Date.valueOf(task.getActualStartDate()) : null);
            pstmt.setDate(9, task.getActualEndDate() != null ? Date.valueOf(task.getActualEndDate()) : null);
            pstmt.setInt(10, task.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deleta uma tarefa do banco de dados pelo seu ID.
     * @param taskId O ID da tarefa a ser deletada.
     * @return true se a deleção for bem-sucedida, false caso contrário.
     */
    public boolean deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método auxiliar para mapear uma linha de um ResultSet para um objeto Task.
     * @param rs O ResultSet posicionado na linha a ser mapeada.
     * @return Um objeto Task preenchido.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private Task mapRowToTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setProjectId(rs.getInt("project_id"));
        task.setResponsibleId(rs.getInt("responsible_id"));
        task.setStatus(TaskStatus.valueOf(rs.getString("status")));

        Date plannedStartDate = rs.getDate("planned_start_date");
        if (plannedStartDate != null) {
            task.setPlannedStartDate(plannedStartDate.toLocalDate());
        }

        Date plannedEndDate = rs.getDate("planned_end_date");
        if (plannedEndDate != null) {
            task.setPlannedEndDate(plannedEndDate.toLocalDate());
        }

        return task;
    }
}
