package com.example.votesapp.activities.opciones_votacion;

public class Modelo_OpVt {

    //private int image;
    private String title;
    private String desc;

    public Modelo_OpVt(String title, String desc) {
        //this.image = image;
        this.title = title;
        this.desc = desc;
    }

   /* public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    */
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

    @Override
    public String toString() {
        return "Modelo_OpVt{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
