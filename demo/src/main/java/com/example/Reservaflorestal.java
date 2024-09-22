package com.example;
import java.io.IOException;
import java.util.*;
public class Reservaflorestal {

    private int intervaloE = 196;
    private int intervaloA = 16;
    private Scanner sf = new Scanner(System.in);
    private ArrayList<Area> areas = new ArrayList<>();
    private ArrayList<Especie> especies = new ArrayList<>();
    private ArrayList<Avistamento> avistamentos = new ArrayList<>();

    public void nomedaReserva(String nomedaReserva) {
        System.out.println(nomedaReserva);
    }

    public void RegistrarEspecie(){
        if (intervaloE == 200) {
            System.out.println("O limite foi atingido, logo nao é capaz de adicionar");
        }
        else{
        intervaloE++;
        Especie animal = new Especie();
        animal.RegistrarEspecie();
        especies.add(animal);
        }
    }
    public void MostrarTodasEspecies(){
        System.out.println("Lista das especies registradas");
        if (especies.isEmpty()){
            System.out.println("Nenhuma especie registrada");
        }
        else{
            for(Especie espe : especies){
                espe.ListarDadosEspecies();
            }
        }
    }


    public void RegistrarArea(){
        if (intervaloA == 20){
            System.out.println("O limite foi atingido, logo nao é capaz de adicionar");
        }
        else{
            intervaloA++;
            Area local = new Area();
            local.definirArea();
            areas.add(local);
        }
    }
    public void MostrarTodasAreas(){
        System.out.println("Lista das areas registradas");
        if (areas.isEmpty()){
            System.out.println("Nenhuma area registrada");
        }
        else{
            for(Area local: areas){
                local.mostrarAreaDados();
            }
        }
    }

    public void RegistrarAvistamento(){
        if (areas.isEmpty()){
            System.out.println("Sem registro de areas");
        }
        else if (especies.isEmpty()){
            System.out.println("Sem especie registrada na lista");
        }
        else{
            System.out.println("Escolha a area em que a especie foi avistada");
            System.out.println("Digitando o ID indicado na lista");
            System.out.println("Aperte Enter para continuar");
            //codigo abaixo funciona igual o system pause do C
            try {
                System.in.read();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            for(int i = 0; i < areas.size(); i++){
                System.out.println("ID [" + (i+1) + "] Nome: " + areas.get(i).getNome());
            }
            System.out.println("Selecione um ID");
            int areaID = 0;
            areaID = sf.nextInt();
            sf.nextLine();
            Area areaSELECIONADA = areas.get(areaID - 1);

            System.out.println();
            System.out.println("Selecione a especie que foi avistada");
            System.out.println("Digitando o ID indicado na lista");
            System.out.println("Aperte Enter para continuar");
            try {
                System.in.read();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            for(int i = 0; i < especies.size(); i++){
                System.out.println("ID [" + (i+1) + "] Nome: " + especies.get(i).getNome());
            }
            System.out.println("Selecione um ID");
            int especieID = 0;
            especieID = sf.nextInt();
            sf.nextLine();
            Especie especieSELECIONADA = especies.get(especieID - 1);
            System.out.println();
            System.out.println("Digite a data que foi avistado (exemplo 01/01/2024)");
            String dataAVISTAMENTO = sf.nextLine();

            Avistamento novoAvistamento = new Avistamento(areaSELECIONADA, especieSELECIONADA, dataAVISTAMENTO);
            avistamentos.add(novoAvistamento);
            System.out.println("Registramento com sucesso");
        }
    }
    public void MostrarTodosAvistamentos() {
        System.out.println("Lista de avistamentos registrados");
        if (avistamentos.isEmpty()) {
            System.out.println("Nenhum avistamento registrado");
        } else {
            for (Avistamento avistamento : avistamentos) {
                avistamento.mostrarAvistamento();
                System.out.println();
            }
        }
    }
    

}
