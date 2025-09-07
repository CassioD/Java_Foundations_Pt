package com.example.dao;

import org.mindrot.jbcrypt.BCrypt;
import com.example.model.User;
import com.example.model.User.UserProfile;
import com.example.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade User.
 * Esta classe encapsula toda a lógica de acesso ao banco de dados (CRUD) para os usuários,
 * separando as regras de negócio da persistência de dados.
 */
public class UserDAO {

    /**
     * Autentica um usuário com base no login e senha.
     * A senha é verificada de forma segura usando BCrypt.
     *
     * @param login O nome de usuário para autenticar.
     * @param plainPassword A senha em texto plano fornecida pelo usuário.
     * @return Um objeto User preenchido se a autenticação for bem-sucedida, caso contrário, null.
     */
    public User authenticate(String login, String plainPassword) {
        // Query SQL para buscar um usuário pelo seu login.
        String sql = "SELECT * FROM users WHERE login = ?";
        User user = null;

        // O 'try-with-resources' garante que a conexão (conn) e o PreparedStatement (pstmt)
        // sejam fechados automaticamente no final do bloco, mesmo que ocorram exceções.
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Define o valor do primeiro parâmetro (?) na query SQL.
            // Isso previne SQL Injection.
            pstmt.setString(1, login);

            // Executa a consulta e obtém o resultado em um ResultSet.
            try (ResultSet rs = pstmt.executeQuery()) {
                // Verifica se a consulta retornou algum registro.
                if (rs.next()) {
                    // Obtém o hash da senha armazenado no banco de dados.
                    String hashedPasswordFromDB = rs.getString("password");

                    // Usa a biblioteca BCrypt para comparar a senha em texto plano com o hash do banco.
                    // Esta função é segura e lida com o 'salt' embutido no hash.
                    if (BCrypt.checkpw(plainPassword, hashedPasswordFromDB)) {
                        // Se as senhas correspondem, mapeia o resultado da linha para um objeto User.
                        user = mapRowToUser(rs);
                    }
                }
            }
        } catch (SQLException e) {
            // Em caso de erro de SQL, imprime o erro para depuração.
            e.printStackTrace();
            // Em uma aplicação real, um sistema de logging seria mais apropriado.
        }
        // Retorna o objeto User (se autenticado) ou null (se a autenticação falhou).
        return user;
    }

    /**
     * Adiciona um novo usuário ao banco de dados.
     * A senha do usuário é criptografada com BCrypt antes de ser salva.
     *
     * @param user O objeto User contendo os dados do novo usuário. A senha deve estar em texto plano.
     * @return true se o usuário foi adicionado com sucesso, false caso contrário.
     */
    public boolean addUser(User user) {
        // Gera um 'salt' e criptografa a senha em texto plano do usuário.
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // Query SQL para inserir um novo usuário.
        String sql = "INSERT INTO users (full_name, cpf, email, job_title, login, password, profile) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Define os valores para cada parâmetro (?) na query.
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getCpf());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getJobTitle());
            pstmt.setString(5, user.getLogin());
            pstmt.setString(6, hashedPassword); // Salva a senha já criptografada.
            pstmt.setString(7, user.getProfile().name()); // Converte o Enum para String.

            // Executa o comando de inserção. executeUpdate() retorna o número de linhas afetadas.
            // Se for > 0, a inserção foi bem-sucedida.
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Erros podem ocorrer por várias razões, como violação de chave única (login, cpf, email).
            e.printStackTrace(); // Idealmente, logar o erro
            return false;
        }
    }

    /**
     * Busca no banco de dados todos os usuários que podem ser gerentes de projeto.
     * Neste caso, são os usuários com perfil 'ADMINISTRADOR' ou 'GERENTE'.
     *
     * @return Uma lista de objetos User que são gerentes.
     */
    public List<User> getManagerUsers() {
        List<User> managers = new ArrayList<>();
        // Query para selecionar usuários que são administradores ou gerentes.
        String sql = "SELECT * FROM users WHERE profile = 'ADMINISTRADOR' OR profile = 'GERENTE'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Itera sobre o resultado da consulta.
            while (rs.next()) {
                // Mapeia cada linha para um objeto User e adiciona à lista.
                managers.add(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Logar o erro.
        }
        return managers;
    }

    /**
     * Busca todos os usuários cadastrados no banco de dados.
     *
     * @return Uma lista com todos os objetos User.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY full_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Logar o erro.
        }
        return users;
    }


    /**
     * Método auxiliar para mapear uma linha de um ResultSet para um objeto User.
     * Reutiliza a lógica de criação de objetos User a partir de dados do banco.
     *
     * @param rs O ResultSet posicionado na linha a ser mapeada.
     * @return Um objeto User preenchido com os dados da linha.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFullName(rs.getString("full_name"));
        user.setLogin(rs.getString("login"));
        // Converte a string do banco de dados de volta para o enum UserProfile.
        user.setProfile(UserProfile.valueOf(rs.getString("profile")));
        user.setEmail(rs.getString("email"));
        user.setJobTitle(rs.getString("job_title"));
        user.setCpf(rs.getString("cpf"));
        // Nota de segurança: A senha (mesmo o hash) nunca é carregada para o objeto User
        // que será usado na aplicação, para evitar exposição acidental.
        return user;
    }
}