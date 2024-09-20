package com.example;
import java.util.*;
public class Reservaflorestal {

    
    private String nomedaReserva = "Amazonia Legal";
    Scanner sf = new Scanner(System.in);
    Area[] area = new Area[3];
    Especie[] especie = new Especie[2];

    public void nomedaReserva(){
        System.out.println(nomedaReserva);
    }

    public Reservaflorestal(){
        for(int i = 0; i < especie.length; i++){
            especie[i] = new Especie();
        }
        for(int i = 0; i < area.length; i++){
            area[i] = new Area();
        }
    }

    public void TODASmostrarArea(){

    }
    public void mostrarAreaESPECIFICA(){

    }
    public void TODASmostrarEspecie(){
        
    }
    public void mostrarEspecieESPECIFICA(){

    }

    public void registrarEspecieAvistada(){
        System.out.println("Lista da área");
        for(int i = 0; i < area.length; i++){
            if(area[i] == null){
                System.out.println("O software não tem uma área registrada");
            }
            else{
                System.out.println("["+i+"]"+area[i].getNome());
            }
            System.out.println("Escolha uma das áreas que foi avistado a especie");
        }
    }

    public void listarEspeciesAvistadas(){

    }


}
