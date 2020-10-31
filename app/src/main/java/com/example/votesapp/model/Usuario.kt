package com.example.votesapp.model

import java.util.*

class Usuario {
    var username : String? = null
    var nombre : String? = null
    var apellido : String? = null
    var correoElectronico : String? = null
    var dni : String? = null
    var contrasenia : String? = null
    var fechaNacimiento: String? = null

    constructor() {}

    constructor(username: String?) {
        this.username = username
    }

    constructor(username: String?, nombre: String?) {
        this.username = username
        this.nombre = nombre
    }

    override fun toString(): String {
        return "Usuario(username=$username, nombre=$nombre, apellido=$apellido, correoElectronico=$correoElectronico, dni=$dni)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Usuario

        if (username?.toUpperCase(Locale.ROOT) != other.username?.toUpperCase(Locale.ROOT)) return false

        return true
    }

    override fun hashCode(): Int {
        return username?.hashCode() ?: 0
    }


}