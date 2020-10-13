package com.example.votesapp.activities.registro_usuario

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.votesapp.R
import com.example.votesapp.activities.mainActivity.MainActivity
import com.example.votesapp.services.RegistroService
import kotlinx.android.synthetic.main.activity_registro_usuario2.*

class Registro_usuario2 : AppCompatActivity() {
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

        fechaNacimiento.setOnClickListener{
            showDatePickerDialog()
        }


        //cuando presiona el boton registrar guarda el registro del usuario
        registrar.setOnClickListener(View.OnClickListener {
            //Trae los datos de la primera pantalla
            val intent = intent
            val bundle = intent.extras
            val userName = bundle!!["userName"] as String?
            val nombre = bundle!!["nombre"] as String?
            val apellido = bundle["apellido"] as String?
            val correo = bundle["correo"] as String?
            val contrasenia = bundle["contrasenia"] as String?
            this.createRegistro(userName.toString(),nombre.toString(),apellido.toString(),correo.toString(),contrasenia.toString(),
                dni.text,fechaNacimiento.text)
            val intent2 = Intent(this, MainActivity::class.java);
            startActivity(intent2);

        })
    }

    private fun createRegistro(userName: Any,nombre:Any,apellido:Any,correo:Any,contrasenia:Any,dni:Any,fechaNacimiento:Any){
        registroService.create(this,userName,nombre,apellido,correo,contrasenia,dni,fechaNacimiento)
    }

    //Para la fecha de Nacimiento
    private fun showDatePickerDialog(){
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = year.toString() + "-" + (month + 1) + "-" + day
            fechaNacimientoUsuario.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }
}