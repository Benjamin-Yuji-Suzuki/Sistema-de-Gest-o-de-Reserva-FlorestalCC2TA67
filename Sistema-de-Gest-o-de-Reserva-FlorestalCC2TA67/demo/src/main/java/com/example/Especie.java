package com.example;
import java.util.*;
public class Especie {

    Scanner sf = new Scanner(System.in);
    private String nome; private String tipo; private String condicao;

    public void RegistrarEspecie() {
        System.out.println("Digite o nome do animal");
        this.nome = sf.nextLine();
        System.out.println("Digite o tipo do animal");
        this.tipo = sf.nextLine();
        System.out.println("Está ameaçado? (sim/nao)");
        this.condicao = sf.nextLine();
    }

    public void ListarEspecies(){
        System.out.println("Nome: "+nome);
        System.out.println("Tipo: "+tipo);
        System.out.println("Está ameaçado? "+condicao);
        System.out.println();
    }
    
}
