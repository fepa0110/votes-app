package com.example.votesapp.model;

public class Seguridad {

    private int id;
    private String modeloSmartphone;
    private String idSmartPhone;

    public Seguridad(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdSmartPhone(String idSmartPhone) {
        this.idSmartPhone = idSmartPhone;
    }

    public String getIdSmartPhone() {
        return idSmartPhone;
    }

    public String getModeloSmartphone() {
        return modeloSmartphone;
    }

    public void setModeloSmartphone(String modeloSmartphone) {
        this.modeloSmartphone = modeloSmartphone;
    }
}
