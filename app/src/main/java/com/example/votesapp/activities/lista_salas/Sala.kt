package com.example.votesapp.activities.lista_salas

class Sala {
    //Atributos
    var id: String? = null
    var nombreSala: String? = null
    var contrasenia:String? = null
    var estado: String? = null

    constructor() {}
    constructor(id: String?, nombreSala: String?, contrasenia: String?) {
        this.id = id
        this.nombreSala = nombreSala
        this.contrasenia=contrasenia
    }
}