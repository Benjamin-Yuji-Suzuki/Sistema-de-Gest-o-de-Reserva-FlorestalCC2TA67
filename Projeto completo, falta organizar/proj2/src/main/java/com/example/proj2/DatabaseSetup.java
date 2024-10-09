
package com.example.proj2;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {

    public static void criarTabelas() {
        // Criação da tabela de espécies
        String sqlCriarTabelaEspecie = "CREATE TABLE IF NOT EXISTS Especie ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " nome TEXT NOT NULL, "
                + " tipo TEXT, "
                + " condicao TEXT"
                + ");";

        // Criação da tabela de áreas
        String sqlCriarTabelaArea = "CREATE TABLE IF NOT EXISTS Area ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " nome TEXT NOT NULL, "
                + " latitude REAL, "
                + " longitude REAL, "
                + " hectares REAL"
                + ");";

        // Criação da tabela de avistamentos
        String sqlCriarTabelaAvistamento = "CREATE TABLE IF NOT EXISTS Avistamento (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " area_nome TEXT NOT NULL, " +  // Nome da área em vez de id_area
                " especie_nome TEXT NOT NULL, " +  // Nome da espécie em vez de id_especie
                " data TEXT NOT NULL" +  // Data do avistamento
                ");";

        // Executa os comandos SQL para criar as tabelas
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCriarTabelaEspecie);
            stmt.execute(sqlCriarTabelaArea);
            stmt.execute(sqlCriarTabelaAvistamento);
            System.out.println("Tabelas criadas com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}

