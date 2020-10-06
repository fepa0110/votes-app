package com.example.votesapp.activities.crear_sala

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.votesapp.R
import com.example.votesapp.activities.mainActivity.MainActivity

import com.example.votesapp.services.SalaService

class CrearSala : AppCompatActivity() {
    val url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas";
    val salaService = SalaService();

    //Etiqueta de log
    companion object {
        val LOG_TAG = "votes"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sala)
        val botonAceptar = findViewById<Button>(R.id.crear_sala_button_aceptar);
        val botonCancelar = findViewById<Button>(R.id.crear_sala_button_cancelar);
        val editTextNombreSala = findViewById<EditText>(R.id.op_vt_editText_titulo);

        //Agregar acción de clickeo
        botonAceptar.setOnClickListener(View.OnClickListener {
            this.createSala(editTextNombreSala.text)
        })

        //Agregar acción de clickeo
        botonCancelar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        })
    }

    private fun createSala(nombre: Any){
        if(nombre.toString().isNotEmpty() and nombre.toString().isNotBlank()){
            salaService.create(this,nombre)
        }
        else Toast.makeText(this, "Por favor escriba un nombre sala", Toast.LENGTH_SHORT).show()
    }
}