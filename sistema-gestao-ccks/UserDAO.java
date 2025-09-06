package com.example.dao;

import com.example.model.User;
import com.example.model.User.UserProfile;
import com.example.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User findByLoginAndPassword(String login, String password) {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?"; // Em um projeto real, a senha deve ser hasheada!
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setLogin(rs.getString("login"));
                    user.setProfile(UserProfile.valueOf(rs.getString("profile")));
                    // Preencha os outros campos se necessário
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratar exceção
        }
        return user;
    }
    // Aqui você adicionaria outros métodos: addUser, updateUser, deleteUser, findById, etc.
}