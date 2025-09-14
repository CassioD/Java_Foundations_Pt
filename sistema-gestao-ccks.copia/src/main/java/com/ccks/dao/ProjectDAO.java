package com.ccks.dao;

import com.ccks.model.Project;
import com.ccks.model.ProjectStatus;
import com.ccks.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 * DAO para a entidade Project. Encapsula a lógica de acesso ao banco de dados para projetos.
 */
public class ProjectDAO {

    /**
     * Adiciona um novo projeto ao banco de dados.
     * @param project O objeto Project a ser salvo.
     * @return true se a inserção for bem-sucedida, false caso contrário.
     */
    public boolean addProject(Project project) {
        String sql = "INSERT INTO projects (name, description, start_date, planned_end_date, status, manager_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, project.getName());
            pstmt.setString(2, project.getDescription());
            pstmt.setDate(3, Date.valueOf(project.getStartDate()));
            pstmt.setDate(4, Date.valueOf(project.getPlannedEndDate()));
            pstmt.setString(5, project.getStatus().name());
            pstmt.setInt(6, project.getManagerId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca todos os projetos cadastrados no banco de dados.
     * @return Uma lista de objetos Project.
     */
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects ORDER BY name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                projects.add(mapRowToProject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    /**
     * Aloca uma equipe a um projeto.
     * @param projectId O ID do projeto.
     * @param teamId O ID da equipe.
     * @return true se a alocação for bem-sucedida, false caso contrário.
     */
    public boolean addTeamToProject(int projectId, int teamId) {
        String sql = "INSERT INTO project_teams (project_id, team_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, teamId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Ignora erro de chave duplicada (a equipe já está no projeto)
            if (e.getErrorCode() != 1062) { // 1062 é o código de erro para 'Duplicate entry' no MySQL
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Atualiza os dados de um projeto existente no banco de dados.
     * @param project O objeto Project com os dados atualizados.
     * @return true se a atualização for bem-sucedida, false caso contrário.
     */
    public boolean updateProject(Project project) {
        String sql = "UPDATE projects SET name = ?, description = ?, start_date = ?, planned_end_date = ?, status = ?, manager_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, project.getName());
            pstmt.setString(2, project.getDescription());
            pstmt.setDate(3, Date.valueOf(project.getStartDate()));
            pstmt.setDate(4, Date.valueOf(project.getPlannedEndDate()));
            pstmt.setString(5, project.getStatus().name());
            pstmt.setInt(6, project.getManagerId());
            pstmt.setInt(7, project.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deleta um projeto do banco de dados pelo seu ID.
     * @param projectId O ID do projeto a ser deletado.
     * @return true se a deleção for bem-sucedida, false caso contrário.
     */
    public boolean deleteProject(int projectId) {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Pode falhar devido a restrições de chave estrangeira (tarefas associadas, etc.)
            // A cláusula ON DELETE CASCADE no banco de dados lida com isso para tabelas de associação.
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Método auxiliar para mapear uma linha do ResultSet para um objeto Project.
     * @param rs O ResultSet posicionado na linha a ser mapeada.
     * @return Um objeto Project preenchido.
     * @throws SQLException Se ocorrer um erro ao acessar os dados.
     */
    private Project mapRowToProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt("id"));
        project.setName(rs.getString("name"));
        project.setDescription(rs.getString("description"));
        
        // Converte java.sql.Date para java.time.LocalDate
        project.setStartDate(rs.getDate("start_date").toLocalDate());
        project.setPlannedEndDate(rs.getDate("planned_end_date").toLocalDate());
        
        // Converte a string do status para o enum ProjectStatus
        project.setStatus(ProjectStatus.valueOf(rs.getString("status")));
        
        project.setManagerId(rs.getInt("manager_id"));
        
        return project;
    }
}
