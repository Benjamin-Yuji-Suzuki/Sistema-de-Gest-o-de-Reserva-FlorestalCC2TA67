package com.example.proj2;

public class Especie {

    private String nome;
    private String tipo;
    private String condicao;

    // Construtor padrão
    public Especie() {
    }

    // Construtor para inicializar a espécie com dados diretamente
    public Especie(String nome, String tipo, String condicao) {
        this.nome = nome;
        this.tipo = tipo;
        this.condicao = condicao;
    }

    // Métodos getters e setters para acessar e modificar os dados da espécie
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    // Método para listar os dados da espécie (se ainda precisar usar em um terminal, por exemplo)
    public void ListarDadosEspecies() {
        System.out.println("Nome: " + nome);
        System.out.println("Tipo: " + tipo);
        System.out.println("Está ameaçado? " + condicao);
        System.out.println();
    }
}

