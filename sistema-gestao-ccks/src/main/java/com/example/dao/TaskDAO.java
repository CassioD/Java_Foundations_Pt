package com.example.dao;

import com.example.model.Task;
import com.example.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskDAO {

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
            pstmt.setDate(6, Date.valueOf(task.getPlannedStartDate()));
            pstmt.setDate(7, Date.valueOf(task.getPlannedEndDate()));

            // Lida com as datas reais, que podem ser nulas
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
}