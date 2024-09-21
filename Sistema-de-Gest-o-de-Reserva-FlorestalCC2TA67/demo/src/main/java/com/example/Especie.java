package com.example;
import java.util.*;
public class Especie {

    Scanner sf = new Scanner(System.in);
    private String nome; private String tipo; private String condicao;

    public void definirEspecie(){
        System.out.println("Registrar uma especie");
        System.out.println("Nome da especie");
        this.nome = sf.nextLine();
        System.out.println("Tipo do animal:");
        this.tipo = sf.nextLine();
        System.out.println("Está ameaçada?");
        this.condicao = sf.nextLine();
        System.out.println(" ");
    }

    public void mostrarEspecie(){
        System.out.println("Nome da especie: "+nome);
        System.out.println("Tipo do animal: "+tipo);
        System.out.println("Esta ameaçada? "+condicao);
        System.out.println(" ");
    }

    public String getNome() {
        return nome;
    }

    

}
