package com.example.votesapp.activities.registro_usuario

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.votesapp.R
import com.example.votesapp.services.RegistroService

class Registro_usuario2 : AppCompatActivity() {
    val url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios"
    val registroService = RegistroService()

    companion object {
        const val LOG_TAG = "registro"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario2)

        val dni = findViewById<EditText>(R.id.dniUsuario);
        val fechaNacimiento = findViewById<EditText>(R.id.fechaNacimientoUsuario);
        val registrar = findViewById<Button>(R.id.bontonRegistrar)

        registrar.setOnClickListener(View.OnClickListener {
                //Trae los datos de la primera pantalla
                val intent = intent
                val bundle = intent.extras
                val nombre = bundle!!["nombre"] as String?
                val apellido = bundle["apellido"] as String?
                val correo = bundle["correo"] as String?
                val contrasenia = bundle["contrasenia"] as String?
            this.createRegistro(nombre.toString(),apellido.toString(),correo.toString(),contrasenia.toString(),dni.text)


        })
    }

    private fun createRegistro(nombre:Any,apellido:Any,correo:Any,contrasenia:Any,dni:Any){
        registroService.create(this,nombre,apellido,correo,contrasenia,dni)

    }

}