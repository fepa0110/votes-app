package com.example.votesapp.activities.crear_sala

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.votesapp.R
import com.example.votesapp.activities.mainActivity.MainActivity

import com.example.votesapp.services.SalaService

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
        botonAceptar.setOnClickListener {
            this.createSala(editTextNombreSala.text)
            finish()
        }

        //Agregar acción de clickeo
        botonCancelar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun createSala(nombre: Any){
        if(nombre.toString().isNotEmpty() and nombre.toString().isNotBlank()){
            salaService.create(this,nombre,username.toString())
        }
        else Toast.makeText(this, "Por favor escriba un nombre sala", Toast.LENGTH_SHORT).show()
    }
}