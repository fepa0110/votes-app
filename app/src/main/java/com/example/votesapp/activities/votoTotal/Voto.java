package com.example.votesapp.activities.votoTotal;

public class Voto {
    private int id;
    private String nombreSala;
    private int cantidadVoto;

    public Voto() {
    }


    public  Voto(int id,String nombreSala,int cantidadVoto){
        this.id = id;
        this.nombreSala=nombreSala;
        this.cantidadVoto=cantidadVoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public int getCantidadVoto() {
        return cantidadVoto;
    }

    public void setCantidadVoto(int cantidadVoto) {
        this.cantidadVoto = cantidadVoto;
    }
}
