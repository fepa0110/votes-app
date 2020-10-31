package com.example.votesapp.activities.opciones_votacion;

public class Modelo_OpVt {

    //private int image;
    private int id;
    private String title;
    private String desc;
    private int cantVotos;

    public Modelo_OpVt(int id, String title, String desc, int cantVotos) {
        //this.image = image;
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.cantVotos = cantVotos;
    }

   /* public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCantVotos() {
        return cantVotos;
    }

    public void setCantVotos(int cantVotos) {
        this.cantVotos = cantVotos;
    }

    @Override
    public String toString() {
        return "Modelo_OpVt{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", cantVotos='" + cantVotos + '\'' +
                '}';
    }
}
