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

import com.example.votesapp.services.SalaService

class CrearSala : AppCompatActivity() {
    private val salaService = SalaService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sala)
        val botonAceptar = findViewById<Button>(R.id.crear_sala_button_aceptar);
        val botonCancelar = findViewById<Button>(R.id.crear_sala_button_cancelar);
        val editTextNombreSala = findViewById<EditText>(R.id.crear_sala_editText_nombre);

        //Agregar acción de clickeo
        botonAceptar.setOnClickListener(View.OnClickListener {
            this.createSala(editTextNombreSala.text)
        })

        //Agregar acción de clickeo
        botonCancelar.setOnClickListener(View.OnClickListener {
            finishActivity()
        })
    }

    private fun createSala(nombre: Any){
        if(nombre.toString().isNotEmpty() and nombre.toString().isNotBlank()){
            salaService.create(this,nombre)
        }
        else Toast.makeText(this, "Por favor escriba un nombre sala", Toast.LENGTH_SHORT).show()
    }
}