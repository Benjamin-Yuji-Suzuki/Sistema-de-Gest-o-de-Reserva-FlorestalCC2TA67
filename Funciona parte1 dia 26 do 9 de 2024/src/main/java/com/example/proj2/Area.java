package com.example.proj2;
import java.util.*;
public class Area {
    

    Scanner sf = new Scanner(System.in);
    private String nome; private String localizacao; private Double tamanho;

    public void definirArea(){
        System.out.println("Nome da area");
        this.nome = sf.nextLine();
        System.out.println("Onde fica?");
        this.localizacao = sf.nextLine();
        System.out.println("Qual tamanho dela em hectares? (digite em numeros)");
        this.tamanho = sf.nextDouble();
        sf.nextLine();
        System.out.println(" ");
    }

    public void mostrarAreaDados(){
        System.out.println("Nome da area: "+nome);
        System.out.println("Local: "+localizacao);
        System.out.println("Tamanho em hectares: "+tamanho);
        System.out.println(" ");
    }

    public String getNome() {
        return nome;
    }
    
}
