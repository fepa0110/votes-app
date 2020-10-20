package com.example.votesapp.services

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.activities.mainActivity.MainActivity
import com.example.votesapp.model.Usuario
import org.json.JSONObject

class RegistroService {
    private val url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios";

    companion object {
        const val LOG_TAG = "registro"
    }

    public fun create(context: Context,usuario: Usuario){
        val queue = Volley.newRequestQueue(context)



        val jsonUsuario = JSONObject()
        jsonUsuario.put("username",usuario.username)
        jsonUsuario.put("nombre",usuario.nombre)
        jsonUsuario.put("apellido",usuario.apellido)
        jsonUsuario.put("correoElectronico",usuario.correoElectronico)
        jsonUsuario.put("contrasenia",usuario.contrasenia)
        jsonUsuario.put("dni",usuario.dni)
        jsonUsuario.put("fechaNacimiento",usuario.fechaNacimiento)


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