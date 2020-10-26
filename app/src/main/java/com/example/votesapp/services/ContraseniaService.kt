package com.example.votesapp.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ContraseniaService {
    private val url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas/contrasenia"

    companion object {
        const val LOG_TAG = "votes"
    }

    public fun create(context: Context, contrasenia: Any, id: Int){
        val queue = Volley.newRequestQueue(context)
        val jsonSala = JSONObject()
        jsonSala.put("contrasenia", contrasenia)


        val jsonRequest = JsonObjectRequest("$url/$id", jsonSala,
            { response ->
                Log.i(LOG_TAG, "Response is: $response")
                Toast.makeText(context, "Contraseña creada correctamente", Toast.LENGTH_SHORT).show()
            },
            { error ->
                error.printStackTrace()
                Log.e(LOG_TAG, "No se pudo crear la contrasenia")
                Toast.makeText(context, "No se pudo crear la Contraseña", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonRequest)
    }
}
