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
    private val url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas/";

    companion object {
        const val LOG_TAG = "votes"
    }

    public fun create(context: Context, nombre: Any){
        val queue = Volley.newRequestQueue(context)

        val jsonSala = JSONObject()
        jsonSala.put("nombre", nombre)

        val jsonRequest = JsonObjectRequest(url, jsonSala,
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

    /*public fun findByUser(context: Context, username: Any){
        val urlByUser = "$url/user/$username"
        val queue = Volley.newRequestQueue(context)

        val jsonArrayRequest = JsonArrayRequest(urlByUser,
            {response ->
                Log.i(LOG_TAG, "Response is: $response")
                Toast.makeText(context, "Salas recuperadas con exito.", Toast.LENGTH_SHORT).show()
            },
            { error ->
                error.printStackTrace()
                Toast.makeText(context, "Hubo un error al recuperar las salas.", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonArrayRequest)
    }*/

   /* public fun findByUser(context: Context, username: String){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create<SalaApiService>(SalaApiService::class.java)

        service.getSalasByUser(username).enqueue(object : Callback<List<Sala>> {
            override fun onResponse(
                call: Call<List<Sala>>,
                response: retrofit2.Response<List<Sala>>
            ) {
                val salas = response?.body()
                Log.i(LOG_TAG, Gson().toJson(salas))
                Toast.makeText(context, "Salas recuperadas con exito.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<List<Sala>>, t: Throwable) {
                t?.printStackTrace()
                Toast.makeText(context, "Hubo un error al recuperar las salas.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }*/
}