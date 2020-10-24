package com.example.votesapp.activities.mis_salas

class Sala {
    //Atributos
    var id: String? = null
    var nombreSala: String? = null
    var contrasenia: String? = null

    constructor() {}
    constructor(id: String?, nombreSala: String?, contrasenia : String?) {
        this.id = id
        this.nombreSala = nombreSala
        this.contrasenia= contrasenia
    }

    override fun toString(): String {
        return "Sala(id=$id, nombreSala=$nombreSala)"
    }
}