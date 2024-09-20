package com.example;

public class Main {
    public static void main(String[] args) {
        
        Reservaflorestal rf = new Reservaflorestal();



        rf.nomedaReserva();
        for(int i = 0; i < rf.especie.length; i++){
            rf.especie[i].definirEspecie();
            rf.area[i].definirArea();
        }
        

    }
}