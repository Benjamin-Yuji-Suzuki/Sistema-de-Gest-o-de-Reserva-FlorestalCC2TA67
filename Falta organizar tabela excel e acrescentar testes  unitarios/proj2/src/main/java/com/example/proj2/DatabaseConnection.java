package com.example.proj2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;
    
    public static Connection getConnection() {
    Connection conn = null;
    try {
        String url = "jdbc:sqlite:meu_banco.db"; // Caminho do banco SQLite
        conn = DriverManager.getConnection(url);
        System.out.println("Conexão com o banco de dados estabelecida.");
    } catch (SQLException e) {
        System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        e.printStackTrace();
    }
    return conn;
}



    // Método para verificar se a conexão está fechada
    public static boolean isClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

    public static void closeConnection() {
    if (connection != null) {
        try {
            connection.close();
            System.out.println("Conexão com o banco de dados fechada.");
            connection = null; // Define a conexão como nula para evitar reutilização
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
}

}

