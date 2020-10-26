package com.example.votesapp.activities.crear_sala

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.votesapp.R
import com.example.votesapp.activities.mainActivity.MainActivity

import com.example.votesapp.services.SalaService
import com.google.android.material.snackbar.Snackbar

class CrearSala : AppCompatActivity() {
    private val salaService = SalaService()

    private var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sala)
        val botonAceptar = findViewById<Button>(R.id.crear_sala_button_aceptar)
        val botonCancelar = findViewById<Button>(R.id.crear_sala_button_cancelar)
        val editTextNombreSala = findViewById<EditText>(R.id.crear_sala_editText_nombre)

        username = intent.getStringExtra("param_username")

        //Agregar acción de clickeo
        botonAceptar.setOnClickListener { view ->
            this.createSala(editTextNombreSala.text, view)
        }

        //Agregar acción de clickeo
        botonCancelar.setOnClickListener {
            finish()
        }
    }

    private fun createSala(nombre: Any, view: View){
        if(nombre.toString().isNotEmpty() and nombre.toString().isNotBlank()){
            salaService.create(this,nombre,username.toString())
            val handler = Handler()
            handler.postDelayed({ onBackPressed() }, 500)
        }
        else {
            Snackbar.make(view, "Por favor escriba un nombre de sala", Snackbar.LENGTH_LONG).show()
            //finish()
        }
    }
}