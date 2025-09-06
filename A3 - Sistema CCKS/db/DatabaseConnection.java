package com.seuprojeto.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/gestao_projetos";
    private static final String USER = "seu_usuario_mysql"; // Troque pelo seu usuário
    private static final String PASSWORD = "sua_senha_mysql"; // Troque pela sua senha

    /**
     * Fornece uma nova conexão com o banco de dados a cada chamada.
     * A responsabilidade de fechar a conexão é de quem a solicita.
     * @return uma nova instância de Connection.
     * @throws SQLException se a conexão falhar.
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }
}