package com.example.dao;

import com.example.model.Team;
import com.example.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.example.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {

    /**
     * Adiciona uma nova equipe e seus membros ao banco de dados.
     * Usa uma transação para garantir que a equipe e seus membros sejam salvos atomicamente.
     * @param team O objeto Team a ser salvo.
     * @param memberIds A lista de IDs dos usuários que são membros da equipe.
     * @return true se a operação for bem-sucedida, false caso contrário.
     */
    public boolean addTeam(Team team, List<Integer> memberIds) {
        String insertTeamSQL = "INSERT INTO teams (name, description) VALUES (?, ?)";
        String insertMembersSQL = "INSERT INTO team_members (team_id, user_id) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            // Desabilita o auto-commit para controlar a transação manualmente
            conn.setAutoCommit(false);

            // 1. Inserir a equipe e obter o ID gerado
            int teamId;
            try (PreparedStatement pstmtTeam = conn.prepareStatement(insertTeamSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmtTeam.setString(1, team.getName());
                pstmtTeam.setString(2, team.getDescription());
                pstmtTeam.executeUpdate();

                try (ResultSet generatedKeys = pstmtTeam.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        teamId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Falha ao criar equipe, nenhum ID obtido.");
                    }
                }
            }

            // 2. Inserir os membros na tabela de associação
            if (memberIds != null && !memberIds.isEmpty()) {
                try (PreparedStatement pstmtMembers = conn.prepareStatement(insertMembersSQL)) {
                    for (Integer memberId : memberIds) {
                        pstmtMembers.setInt(1, teamId);
                        pstmtMembers.setInt(2, memberId);
                        pstmtMembers.addBatch();
                    }
                    pstmtMembers.executeBatch();
                }
            }

            // Se tudo correu bem, comita a transação
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, faz o rollback da transação
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            // Restaura o auto-commit e fecha a conexão
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Atualiza uma equipe existente e seus membros.
     * Usa uma transação para garantir a atomicidade da operação.
     * @param team O objeto Team com nome e descrição atualizados.
     * @param memberIds A nova lista de IDs de membros da equipe.
     * @return true se a operação for bem-sucedida, false caso contrário.
     */
    public boolean updateTeam(Team team, List<Integer> memberIds) {
        String updateTeamSQL = "UPDATE teams SET name = ?, description = ? WHERE id = ?";
        String deleteMembersSQL = "DELETE FROM team_members WHERE team_id = ?";
        String insertMembersSQL = "INSERT INTO team_members (team_id, user_id) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Atualizar dados da equipe
            try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateTeamSQL)) {
                pstmtUpdate.setString(1, team.getName());
                pstmtUpdate.setString(2, team.getDescription());
                pstmtUpdate.setInt(3, team.getId());
                pstmtUpdate.executeUpdate();
            }

            // 2. Deletar membros antigos
            try (PreparedStatement pstmtDelete = conn.prepareStatement(deleteMembersSQL)) {
                pstmtDelete.setInt(1, team.getId());
                pstmtDelete.executeUpdate();
            }

            // 3. Inserir novos membros
            if (memberIds != null && !memberIds.isEmpty()) {
                try (PreparedStatement pstmtInsert = conn.prepareStatement(insertMembersSQL)) {
                    for (Integer memberId : memberIds) {
                        pstmtInsert.setInt(1, team.getId());
                        pstmtInsert.setInt(2, memberId);
                        pstmtInsert.addBatch();
                    }
                    pstmtInsert.executeBatch();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Deleta uma equipe do banco de dados.
     * A exclusão em cascata no banco de dados removerá as associações de membros.
     * @param teamId O ID da equipe a ser deletada.
     * @return true se a deleção for bem-sucedida, false caso contrário.
     */
    public boolean deleteTeam(int teamId) {
        String sql = "DELETE FROM teams WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, teamId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getTeamMembers(int teamId) {
        List<User> members = new ArrayList<>();
        String sql = "SELECT u.* FROM users u JOIN team_members tm ON u.id = tm.user_id WHERE tm.team_id = ?";
        UserDAO userDAO = new UserDAO(); // Helper to map user rows

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, teamId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    members.add(userDAO.mapRowToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    /**
     * Busca todas as equipes cadastradas.
     * @return Uma lista de objetos Team.
     */
    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM teams ORDER BY name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Team team = new Team();
                team.setId(rs.getInt("id"));
                team.setName(rs.getString("name"));
                team.setDescription(rs.getString("description"));
                teams.add(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }
}