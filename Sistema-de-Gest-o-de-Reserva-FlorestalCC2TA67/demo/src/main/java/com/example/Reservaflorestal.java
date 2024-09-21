package com.example;
import java.util.*;
public class Reservaflorestal {

    
    private String nomedaReserva;
    private Scanner sf = new Scanner(System.in);
    private ArrayList<Area> areas = new ArrayList<>();
    private ArrayList<Especie> especies = new ArrayList<>();

    public void nomedaReserva(String nomedaReserva) {
        this.nomedaReserva = nomedaReserva;
        System.out.println(nomedaReserva);
    }

    public void RegistrarEspecie(){

        Especie animal = new Especie();
        animal.RegistrarEspecie();
        especies.add(animal);

    }
    
    public void MostrarEspecies(){
        System.out.println("Lista das especies");
        if (especies.isEmpty()){
            System.out.println("Nenhuma especie registrada");
        }
        else{
            for(Especie espe : especies){
                espe.ListarEspecies();
            }
        }
    }

}
