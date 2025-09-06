package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // ATENÇÃO: Substitua com suas credenciais do MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/gestao_projetos_db";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password"; // <-- COLOQUE SUA SENHA AQUI

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Opcional: Registrar o driver (necessário em ambientes mais antigos)
            // Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            // Em uma aplicação real, trate essa exceção de forma mais robusta
        }
        return connection;
    }
}