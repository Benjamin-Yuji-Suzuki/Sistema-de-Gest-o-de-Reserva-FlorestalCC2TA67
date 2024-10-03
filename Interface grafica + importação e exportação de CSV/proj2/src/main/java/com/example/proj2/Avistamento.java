package com.example.proj2;

public class Avistamento {
    private Area area;
    private Especie especieAvistada;
    private String data;

    public Avistamento(Area area, Especie especieAvistada, String data) {
        this.area = area;
        this.especieAvistada = especieAvistada;
        this.data = data;
    }

    public void mostrarAvistamento() {
        System.out.println("Área: " + area.getNome());
        System.out.println("Espécie avistada: "+especieAvistada.getNome());
        System.out.println("Data do Avistamento: " + data);
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Especie getEspecieAvistada() {
        return especieAvistada;
    }

    public void setEspecieAvistada(Especie especieAvistada) {
        this.especieAvistada = especieAvistada;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public String getAreaNome() {
        return area != null ? area.getNome() : "";
    }
    
    
}
