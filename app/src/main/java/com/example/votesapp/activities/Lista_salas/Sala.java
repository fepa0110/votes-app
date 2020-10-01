package com.example.votesapp.activities.Lista_salas;

public class Sala {
    //Atributos
    private String id;
    private String nombreSala;

    public Sala(){

    }

    public Sala (String id, String nombreSala){
        this.setId(id);
        this.nombreSala=nombreSala;


    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }


}
