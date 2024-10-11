package com.example.proj2;

public class Area {
    

    private String nome; 
    private Double latitude; 
    private Double longitude; 
    private Double hectares;
    
    public Area(String nome, Double latitude, Double longitude, Double hectares) {
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hectares = hectares;
    }

    public Area(){
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getHectares() {
        return hectares;
    }

    public void setHectares(Double hectares) {
        this.hectares = hectares;
    }

    
    
}
