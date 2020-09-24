package com.example.votesapp.services

import android.content.Context
import android.util.Log
import android.util.Log.INFO
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


abstract class SalaService {
    /*val url = "if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas";
    var queue : RequestQueue;

    fun SalaService(context: Context){
       queue = Volley.newRequestQueue(context);
    }

    fun create(nombre: String) {
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response -> // response
                Log.d("Response", response)
            },
            Response.ErrorListener { // error
                Log.i(nombre,"No se pudo crear la sala.");
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["nombre"] = nombre;
                return params
            }
        }

        queue.add(postRequest)
    }*/
}