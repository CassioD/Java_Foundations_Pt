package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária responsável por gerenciar a conexão com o banco de dados MySQL.
 * Centraliza a lógica de conexão em um único ponto, facilitando a manutenção.
 */
public class DatabaseConnection {
    // A URL de conexão JDBC. Define o protocolo (jdbc:mysql), o host (localhost),
    // a porta (3306) e o nome do banco de dados (gestao_projetos_db).
    // O parâmetro `user=root` é uma forma alternativa de passar o usuário, mas o método getConnection o sobrepõe.
    private static final String URL = "jdbc:mysql://localhost:3306/gestao_projetos_db?user=root";
    
    // Usuário do banco de dados. 'root' é o padrão no MySQL.
    private static final String USER = "root";
    
    // Senha do usuário do banco de dados.
    // ATENÇÃO: Substitua pela senha que você configurou no seu MySQL.
    private static final String PASSWORD = "ccks123456"; // <-- COLOQUE SUA SENHA AQUI

    /**
     * Tenta estabelecer uma conexão com o banco de dados.
     * Este método estático pode ser chamado de qualquer lugar da aplicação para obter uma conexão.
     * @return um objeto Connection se a conexão for bem-sucedida, ou null em caso de falha.
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // O DriverManager tenta encontrar um driver adequado a partir da URL JDBC fornecida
            // e estabelecer uma conexão.
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Se uma SQLException ocorrer, significa que a conexão falhou.
            // Pode ser por causa de credenciais erradas, banco de dados offline,
            // nome do banco de dados incorreto na URL, etc.
            
            // Imprime o stack trace do erro no console para depuração.
            e.printStackTrace();
            
            // Exibe uma mensagem de erro mais amigável no console de erro.
            System.err.println("Falha ao conectar ao banco de dados. Verifique a URL, usuário e senha.");
            System.err.println("URL: " + URL);
            
            // Em uma aplicação real, seria ideal usar um sistema de logging (como SLF4J/Logback)
            // e talvez lançar uma exceção customizada para ser tratada pelas camadas superiores.
            return null; // Retorna nulo para indicar falha na conexão
        }
        // Retorna a conexão estabelecida com sucesso.
        return connection;
    }
}