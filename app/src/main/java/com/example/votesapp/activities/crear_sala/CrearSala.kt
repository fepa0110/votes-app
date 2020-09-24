package com.example.votesapp.activities.crear_sala

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.StringRequest
import com.example.votesapp.R
import com.example.votesapp.activities.mainActivity.MainActivity


class CrearSala : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sala)
    }

    fun backToMain(view: View){
        val intent = Intent(this, MainActivity::class.java);
        startActivity(intent);
    }

    val url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas";
    //private val queue = Volley.newRequestQueue(baseContext);

    private fun startRequestQueue() : RequestQueue {
        // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

        // Instantiate the RequestQueue with the cache and network. Start the queue.
        return RequestQueue(cache, network).apply {
            start()
        };
    }

    private fun sendRequest(nombre: String) {
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response -> // response
                Log.d("Response", response)
            },
            Response.ErrorListener { // error
                Log.i(nombre, "No se pudo crear la sala.");
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["nombre"] = nombre;
                return params
            }
        }
        this.startRequestQueue().add(postRequest)
    }

    fun createSala(view: View){
        sendRequest("app");
        //sendRequest(crear_sala_editText_nombre.text.toString());
    }
}