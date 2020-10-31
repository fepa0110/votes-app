package com.example.votesapp.model

class Sala {
    //Atributos
    var id: String? = null
    var nombre: String? = null
    var username: String? = null
    var estado: String? = null

    constructor() {}
    constructor(id: String?, nombreSala: String?) {
        this.id = id
        this.nombre = nombreSala
    }

    override fun toString(): String {
        return "Sala(id=$id, nombreSala=$nombre)"
    }
}