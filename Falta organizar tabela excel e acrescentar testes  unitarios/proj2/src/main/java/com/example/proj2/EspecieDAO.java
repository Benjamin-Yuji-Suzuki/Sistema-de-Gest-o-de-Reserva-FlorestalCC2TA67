/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.proj2;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EspecieDAO {

    // Método para inserir uma nova espécie no banco de dados
    public static void inserirEspecie(Especie especie) {
        String sql = "INSERT INTO Especie(nome, tipo, condicao) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Definir os parâmetros de inserção
            pstmt.setString(1, especie.getNome());
            pstmt.setString(2, especie.getTipo());
            pstmt.setString(3, especie.getCondicao());

            // Executar a inserção
            pstmt.executeUpdate();
            System.out.println("Espécie inserida com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir espécie: " + e.getMessage());
        }
    }
    
    public static List<Especie> buscarTodasEspecies() {
        List<Especie> especies = new ArrayList<>();
        String sql = "SELECT * FROM Especie";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Especie especie = new Especie(
                    rs.getString("nome"),
                    rs.getString("tipo"),
                    rs.getString("condicao")
                );
                especies.add(especie);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar espécies: " + e.getMessage());
        }

        return especies;
    }
    
    public static Especie buscarEspeciePorNome(String nomeEspecie) {
        String sql = "SELECT * FROM Especie WHERE nome = ?";
        Especie especie = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeEspecie);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                especie = new Especie(
                    rs.getString("nome"),
                    rs.getString("tipo"),
                    rs.getString("condicao")
                );
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar espécie: " + e.getMessage());
        }

        return especie;  // Retorna a espécie encontrada ou null se não houver
    }
    
    public static void atualizarEspecie(Especie especie, String nomeAntigo) {
    String sql = "UPDATE Especie SET nome = ?, tipo = ?, condicao = ? WHERE nome = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, especie.getNome());
        pstmt.setString(2, especie.getTipo());
        pstmt.setString(3, especie.getCondicao());
        pstmt.setString(4, nomeAntigo);

        pstmt.executeUpdate();
        System.out.println("Espécie atualizada com sucesso.");
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar espécie: " + e.getMessage());
    }
}


        
    public void alterarNomeEspecie(String nomeAntigo, String nomeNovo, String tipo, String condicao) {
    // Busca a espécie pelo nome antigo
    Especie especie = EspecieDAO.buscarEspeciePorNome(nomeAntigo);
    if (especie != null) {
        // Atualiza os dados no objeto
        especie.setNome(nomeNovo);
        especie.setTipo(tipo);
        especie.setCondicao(condicao);

        // Atualiza a espécie no banco de dados
        EspecieDAO.atualizarEspecie(especie, nomeAntigo);  // Usa o nome antigo para localizar

        // Atualiza o nome da espécie em todos os avistamentos
        AvistamentoDAO.atualizarEspecieNosAvistamentos(nomeAntigo, nomeNovo);

        System.out.println("Espécie e avistamentos atualizados com sucesso.");
    } else {
        System.out.println("Espécie não encontrada.");
    }
}


    
    
}










