package com.example;

public class Main {
    public static void main(String[] args) {
        
        Reservaflorestal rf = new Reservaflorestal();

        rf.nomedaReserva("Amazonia Legal");
        rf.RegistrarEspecie();
        rf.MostrarTodasEspecies();

        rf.RegistrarArea();
        rf.RegistrarArea();
        rf.RegistrarArea();
        rf.RegistrarArea();
        rf.MostrarTodasAreas();

        rf.MostrarTodosAvistamentos();
        rf.RegistrarAvistamento();
        rf.MostrarTodosAvistamentos();

    }
}