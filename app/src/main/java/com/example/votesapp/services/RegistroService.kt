package com.example.votesapp.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegistroService {
    private val url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios";

    companion object {
        const val LOG_TAG = "registro"
    }

    public fun create(context: Context, userName: Any,nombre: Any, apellido: Any,correo: Any,contrasenia:Any,dni:Any,fechaNacimiento:Any){
        val queue = Volley.newRequestQueue(context)

        val jsonUsuario = JSONObject()
        jsonUsuario.put("username",userName)
        jsonUsuario.put("nombre",nombre)
        jsonUsuario.put("apellido",apellido)
        jsonUsuario.put("correoElectronico",correo)
        jsonUsuario.put("contrasenia",contrasenia)
        jsonUsuario.put("dni",dni)
        jsonUsuario.put("fechaNacimiento",fechaNacimiento)


        val jsonRequest = JsonObjectRequest(url, jsonUsuario,
            { response ->
                Log.i(LOG_TAG, "Response is: $response")
                Toast.makeText(context, "Registro creado correctamente", Toast.LENGTH_SHORT).show()
            },
            { error ->
                error.printStackTrace()
                Log.e(LOG_TAG, "No se pudo crear el registro")
                Toast.makeText(context, "No se pudo crear el registro", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonRequest)
    }

}