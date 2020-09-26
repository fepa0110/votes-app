package com.example.votesapp.activities.crear_sala

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.example.votesapp.R
import com.example.votesapp.activities.mainActivity.MainActivity
import org.json.JSONObject


class CrearSala : AppCompatActivity() {
    val url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas";

    //Etiqueta de log
    companion object {
        val LOG_TAG = "votes"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sala)
        val botonAceptar = findViewById<Button>(R.id.crear_sala_button_aceptar);
        val botonCancelar = findViewById<Button>(R.id.crear_sala_button_cancelar);
        val editTextNombreSala = findViewById<EditText>(R.id.crear_sala_editText_nombre);

        //Agregar acción de clickeo
        botonAceptar.setOnClickListener(View.OnClickListener {
            createSala(editTextNombreSala.text)
        })

        //Agregar acción de clickeo
        botonCancelar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        })
    }
    
    private fun createSala(nombre: Any){
        val queue = Volley.newRequestQueue(this)

        val jsonSala = JSONObject()
        jsonSala.put("nombre",nombre)

        val jsonRequest = JsonObjectRequest(url, jsonSala,
            Response.Listener { response ->
                Log.i(LOG_TAG, "Response is: $response")
                Toast.makeText(this, "Sala creada correctamente", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Log.e(LOG_TAG, "No se pudo crear la sala")
                Toast.makeText(this, "No se pudo crear la sala", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonRequest)
    }

}