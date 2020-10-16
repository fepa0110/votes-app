package com.example.votesapp.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SalaService {
    private val url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas"

    companion object {
        const val LOG_TAG = "votes"
    }

    public fun create(context: Context, nombre: Any, username: String){
        val queue = Volley.newRequestQueue(context)
        val jsonSala = JSONObject()
        jsonSala.put("nombre", nombre)

        val jsonRequest = JsonObjectRequest("$url/$username", jsonSala,
            { response ->
                Log.i(LOG_TAG, "Response is: $response")
                Toast.makeText(context, "Sala creada correctamente", Toast.LENGTH_SHORT).show()
            },
            { error ->
                error.printStackTrace()
                Log.e(LOG_TAG, "No se pudo crear la sala")
                Toast.makeText(context, "No se pudo crear la sala", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonRequest)
    }
}