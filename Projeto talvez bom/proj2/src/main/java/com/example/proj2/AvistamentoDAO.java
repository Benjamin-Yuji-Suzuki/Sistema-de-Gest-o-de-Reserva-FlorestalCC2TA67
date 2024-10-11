/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.proj2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class AvistamentoDAO {

    public static void criarTabelaAvistamentos() {
    String sql = "CREATE TABLE IF NOT EXISTS Avistamento (" +
                 "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 "area_nome TEXT NOT NULL, " +  // Nome da área
                 "especie_nome TEXT NOT NULL, " +  // Nome da espécie
                 "data TEXT NOT NULL" +
                 ");";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.execute();
        System.out.println("Tabela Avistamento criada com sucesso (ou já existia).");
    } catch (SQLException e) {
        System.out.println("Erro ao criar a tabela Avistamento: " + e.getMessage());
    }
}

    public static void inserirAvistamento(Avistamento avistamento) {
    String sql = "INSERT INTO Avistamento (area_nome, especie_nome, data) VALUES (?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, avistamento.getArea().getNome());  // Agora usamos o nome da área
        stmt.setString(2, avistamento.getEspecieAvistada().getNome());  // Nome da espécie
        stmt.setString(3, avistamento.getData());
        stmt.executeUpdate();
        System.out.println("Avistamento registrado com sucesso!");
    } catch (SQLException e) {
        System.out.println("Erro ao registrar avistamento: " + e.getMessage());
    }
}
    
    public static List<Avistamento> buscarTodosAvistamentos() {
    List<Avistamento> avistamentos = new ArrayList<>();
    String sql = "SELECT * FROM Avistamento";

    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        // Verificação para garantir que a consulta retorna resultados
        System.out.println("Iniciando busca de avistamentos no banco de dados...");

        while (rs.next()) {
            // Log para cada linha encontrada
            System.out.println("Avistamento encontrado - Área: " + rs.getString("area_nome") +
                               ", Espécie: " + rs.getString("especie_nome") + ", Data: " + rs.getString("data"));

            // Carregar a área e a espécie
            Area area = AreaDAO.buscarAreaPorNome(rs.getString("area_nome"));
            Especie especie = EspecieDAO.buscarEspeciePorNome(rs.getString("especie_nome"));

            // Verificar se área e espécie não são nulas antes de criar o avistamento
            if (area != null && especie != null) {
                Avistamento avistamento = new Avistamento(area, especie, rs.getString("data"));
                avistamentos.add(avistamento);
                System.out.println("Avistamento adicionado à lista.");
            } else {
                System.out.println("Avistamento ignorado devido a área ou espécie nula.");
            }
        }

    } catch (SQLException e) {
        System.out.println("Erro ao buscar avistamentos: " + e.getMessage());
    }

    // Log do tamanho da lista final
    System.out.println("Total de avistamentos carregados: " + avistamentos.size());
    return avistamentos;
}

    // Método para contar o número de avistamentos no banco de dados
    public static int contarAvistamentos() {
        String sql = "SELECT COUNT(*) AS total FROM Avistamento";
        int total = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Erro ao contar avistamentos: " + e.getMessage());
        }

        return total;
    }

        
    public static void atualizarEspecieNosAvistamentos(String nomeAntigo, String nomeNovo) {
    String sql = "UPDATE Avistamento SET especie_nome = ? WHERE especie_nome = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, nomeNovo);
        pstmt.setString(2, nomeAntigo);

        pstmt.executeUpdate();
        System.out.println("Nome da espécie atualizado em todos os avistamentos.");
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar avistamentos: " + e.getMessage());
    }
}


    
}

