package com.example;
import java.util.*;
public class Area {
    

    Scanner sf = new Scanner(System.in);
    private String nome; private String localizacao; private Double tamanho;

    public void definirArea(){
        System.out.println("Nome da area");
        this.nome = sf.nextLine();
        System.out.println("Onde fica?");
        this.localizacao = sf.nextLine();
        sf.nextLine();
        System.out.println("Qual tamanho dela em hectares? (digite em numeros)");
        this.tamanho = sf.nextDouble();
        System.out.println(" ");
    }

    public String getNome() {
        return nome;
    }
    
}
