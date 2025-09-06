package com.seuprojeto.dao;

import com.seuprojeto.db.DatabaseConnection;
import com.seuprojeto.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario autenticar(String login, String senha) {
        String sql = "SELECT id, nome_completo, perfil, senha_hash FROM usuarios WHERE login = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String senhaHash = rs.getString("senha_hash");
                // Verifica se a senha fornecida corresponde ao hash armazenado
                if (BCrypt.checkpw(senha, senhaHash)) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNomeCompleto(rs.getString("nome_completo"));
                    usuario.setLogin(login);
                    usuario.setPerfil(Usuario.Perfil.valueOf(rs.getString("perfil")));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Tratar exceção adequadamente
        }
        return null; // Retorna nulo se a autenticação falhar
    }

    // Aqui você adicionaria outros métodos: salvar, atualizar, deletar, buscarPorId, etc.
}