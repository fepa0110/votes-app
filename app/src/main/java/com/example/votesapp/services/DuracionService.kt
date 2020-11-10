package com.example.votesapp.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class DuracionService {
    private val url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/duraciones"
    companion object {
        const val LOG_TAG = "duracion"
    }

    public fun create(context: Context, fechaHora: Any, id: Int){
        val queue = Volley.newRequestQueue(context)
        val jsonTiempoVotacion = JSONObject()
        jsonTiempoVotacion.put("fechaHora", fechaHora )


        val jsonRequest = JsonObjectRequest("$url/$id", jsonTiempoVotacion,
            { response ->
                Log.i(ContraseniaService.LOG_TAG, "Response is: $response")
                Toast.makeText(context, "Tiempo de votacion creada correctamente", Toast.LENGTH_SHORT).show()
            },
            { error ->
                error.printStackTrace()
                Log.e(ContraseniaService.LOG_TAG, "No se pudo crear el tiempo")
                Toast.makeText(context, "No se pudo crear el tiempo", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonRequest)
    }



}