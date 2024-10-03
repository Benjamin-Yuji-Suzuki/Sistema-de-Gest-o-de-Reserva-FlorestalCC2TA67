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

public class AreaDAO {

    // Método para inserir uma nova área no banco de dados
    public static void inserirArea(Area area) {
    String sql = "INSERT INTO Area(nome, latitude, longitude, hectares) VALUES(?, ?, ?, ?)";

    // Nova conexão aberta para essa operação
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, area.getNome());
        pstmt.setDouble(2, area.getLatitude());
        pstmt.setDouble(3, area.getLongitude());
        pstmt.setDouble(4, area.getHectares());
        pstmt.executeUpdate();
        System.out.println("Área registrada com sucesso.");
    } catch (SQLException e) {
        System.out.println("Erro ao registrar área: " + e.getMessage());
        e.printStackTrace();
    }

    // A conexão é fechada automaticamente aqui
}


    // Método para buscar todas as áreas no banco de dados
    public static List<Area> buscarTodasAreas() {
        List<Area> areas = new ArrayList<>();
        String sql = "SELECT * FROM Area";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Area area = new Area(
                    rs.getString("nome"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getDouble("hectares")
                );
                areas.add(area);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar áreas: " + e.getMessage());
        }

        return areas;
    }

    // Método para contar o número de áreas no banco de dados
    public static int contarAreas() {
        String sql = "SELECT COUNT(*) AS total FROM Area";
        int total = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Erro ao contar áreas: " + e.getMessage());
        }

        return total;
    }

    // Método para deletar uma área pelo nome
    public static void deletarArea(String nomeArea) {
        String sql = "DELETE FROM Area WHERE nome = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeArea);
            pstmt.executeUpdate();
            System.out.println("Área deletada com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao deletar área: " + e.getMessage());
        }
    }
    
    public static Area buscarAreaPorNome(String nomeArea) {
        String sql = "SELECT * FROM Area WHERE nome = ?";
        Area area = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeArea);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                area = new Area(
                    rs.getString("nome"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getDouble("hectares")
                );
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar área: " + e.getMessage());
        }

        return area;  // Retorna a área encontrada ou null se não houver
    }
}
    


