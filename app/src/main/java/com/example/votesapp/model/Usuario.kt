package com.example.votesapp.model

class Usuario {
    var username : String? = null
    var nombre : String? = null
    var contrasenia : String? = null

    constructor() {}

    constructor(username: String?, nombre: String?) {
        this.username = username
        this.nombre = nombre
    }

    @JvmName("nombre")
    fun getNombre(): String {
        return this.nombre.toString()
    }

    override fun toString(): String {
        return "Usuario(username=$username, nombre=$nombre)"
    }
}