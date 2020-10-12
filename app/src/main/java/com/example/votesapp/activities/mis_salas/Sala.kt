package com.example.votesapp.activities.mis_salas

class Sala {
    //Atributos
    var id: String? = null
    var nombreSala: String? = null

    constructor() {}
    constructor(id: String?, nombreSala: String?) {
        this.id = id
        this.nombreSala = nombreSala
    }

    override fun toString(): String {
        return "Sala(id=$id, nombreSala=$nombreSala)"
    }
}