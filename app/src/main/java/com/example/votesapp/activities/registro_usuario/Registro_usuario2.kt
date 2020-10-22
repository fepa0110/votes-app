package com.example.votesapp.activities.registro_usuario
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.R
import com.example.votesapp.activities.mainActivity.MainActivity
import com.example.votesapp.activities.newLogin.NewLogin
import com.example.votesapp.model.Usuario
import com.example.votesapp.services.RegistroService
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_registro_usuario2.*
import org.json.JSONObject
import java.util.regex.Pattern
import android.text.TextWatcher as TextWatcher
import com.example.votesapp.activities.newLogin.NewLoginService

class Registro_usuario2 : AppCompatActivity() {

    private val urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario2)

        val registroNombre = findViewById<TextInputEditText>(R.id.text_registroNombre)
        val registroApellido = findViewById<TextInputEditText>(R.id.text_registroApellido)
        val registroDni = findViewById<TextInputEditText>(R.id.text_registrodni)
        val registroFechaNacimiento = findViewById<TextInputEditText>(R.id.text_registroFechaNacimiento)
        val cancelar = findViewById<View>(R.id.botonVolver2) as Button
        val registrar = findViewById<View>(R.id.bontonRegistrar) as Button
        registroFechaNacimiento.setOnClickListener(View.OnClickListener { showDatePickerDialog() })

        registroNombre.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int,count: Int,after: Int) { }
            override fun onTextChanged(charSequence: CharSequence,star: Int,before: Int,count: Int) {
                esNombreValido(charSequence.toString())
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        registroApellido.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence,start: Int,count: Int,after: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence,star: Int,before: Int,count: Int) {
                esApellidoValido(charSequence.toString())
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        registroDni.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence,start: Int,count: Int,after: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence,start: Int,before: Int,count: Int) {
                esDniValido(charSequence.toString())
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        registroFechaNacimiento.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence,start: Int,count: Int,after: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence,start: Int,before: Int,count: Int) {
                esFechaNacimientoValido(charSequence.toString())
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        //Boton Cancelar de la segunda pantalla
        cancelar.setOnClickListener{
            val intent = Intent(this, NewLogin::class.java)
            startActivity(intent)
            finish()

        }
        //Boton de la segunda pantalla al presionar registrar
        registrar.setOnClickListener {
            validarDatos()}
    }


    //Validar el nombre de usuario
    private fun esNombreValido(nombre: String): Boolean {
        val nombreLayout = findViewById<TextInputLayout>(R.id.nombreUsuario);
        val patron =
            Pattern.compile("^(?=.{3,15}\$)[A-ZÁÉÍÓÚ][a-zñáéíóú]+(?: [A-ZÁÉÍÓÚ][a-zñáéíóú]+)?\$")
        if (!patron.matcher(nombre).matches()) {
            nombreLayout!!.error = "Nombre invalido"
            return false
        } else {
            nombreLayout!!.error = null
        }
        return true
    }

    //Validar el Apellido de usuario
    private fun esApellidoValido(apellido: String): Boolean {
        val apellidoLayout = findViewById<TextInputLayout>(R.id.apellidoUsuario);
        val patron =
            Pattern.compile("^(?=.{3,15}\$)[A-ZÁÉÍÓÚ][a-zñáéíóú]+(?: [A-ZÁÉÍÓÚ][a-zñáéíóú]+)?\$")
        if (!patron.matcher(apellido).matches()) {
            apellidoLayout!!.error = "Apellido invalido"
            return false
        } else {
            apellidoLayout!!.error = null
        }
        return true
    }
    //Validar el dni de usuario
    private fun esDniValido(dni: String): Boolean {
        val dniLayout=findViewById<TextInputLayout>(R.id.dniUsuario);
        val patron =
            Pattern.compile("^\\d{8}(?:[-\\s]\\d{4})?\$")
        if (!patron.matcher(dni).matches()) {
            dniLayout!!.error = "Dni invalido"
            return false
        } else {
            dniLayout!!.error = null
        }
        return true
    }
    //que el campo de fecha de nacimiento no este vacio
    private fun esFechaNacimientoValido(fecha: String): Boolean {
        val fechaNacimientoLayout = findViewById<TextInputLayout>(R.id.fechaNacimientoUsuario);
//        val patron =
//            Pattern.compile("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])\$")
        if (fecha.isEmpty()) {
            fechaNacimientoLayout!!.error = "Ingrese fecha de nacimiento"
            return false
        } else {
            fechaNacimientoLayout!!.error = null
        }
        return true
    }
    private fun validarDatos() {
        //
        val nombreLayout = findViewById<TextInputLayout>(R.id.nombreUsuario);
        val apellidoLayout = findViewById<TextInputLayout>(R.id.apellidoUsuario);
        val dniLayout=findViewById<TextInputLayout>(R.id.dniUsuario);
        val fechaNacimientoLayout = findViewById<TextInputLayout>(R.id.fechaNacimientoUsuario);

        val registroNombre = findViewById<TextInputEditText>(R.id.text_registroNombre)
        val registroApellido = findViewById<TextInputEditText>(R.id.text_registroApellido)
        val registroDni = findViewById<TextInputEditText>(R.id.text_registrodni)
        val registroFechaNacimiento = findViewById<TextInputEditText>(R.id.text_registroFechaNacimiento)


        val nombre = nombreLayout!!.editText!!.text.toString()
        val apellido = apellidoLayout!!.editText!!.text.toString()
        val dni = dniLayout!!.editText!!.text.toString()
        val fecha = fechaNacimientoLayout!!.editText!!.text.toString()

        val a = esNombreValido(nombre)
        val c = esApellidoValido(apellido)
        val b = esDniValido(dni)
        val d = esFechaNacimientoValido(fecha)
        if (a && b && c && d) {
            val intent = intent
            val bundle = intent.extras
            val usuario = Usuario()
//            usuario.username = bundle!!["userName"] as String?
            usuario.nombre = registroNombre.text.toString()
            usuario.apellido = registroApellido.text.toString()
            usuario.dni=registroDni.text.toString()
            usuario.fechaNacimiento = registroFechaNacimiento.text.toString()
            usuario.correoElectronico = bundle?.get("correo") as String?
            usuario.contrasenia = bundle?.get("contrasenia") as String?
            usuario.username = bundle?.get("userName") as String?
            this.isDniExists(usuario)


        }

    }

    //Para la fecha de Nacimiento
    private fun showDatePickerDialog(){
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = year.toString() + "-" + (month + 1) + "-" + day
            text_registroFechaNacimiento.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }


    private fun loginSuccessfull( userName: String,nombre: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("param_username",userName)
        intent.putExtra("param_usuario_nombre",nombre)
        startActivity(intent)
        finish()
    }

    private fun isDniExists( usuario : Usuario) {
        val queue = Volley.newRequestQueue(this)
        val usuarioService = NewLoginService()
        val jsonUsuario = JSONObject()
        jsonUsuario.put("dni", usuario.dni)

        val registroDni = findViewById<TextInputEditText>(R.id.text_registrodni)
        val dniLayout=findViewById<TextInputLayout>(R.id.dniUsuario);

        val jsonRequest = JsonObjectRequest(
            "$urlBase/dni", jsonUsuario,
            { response ->
                Log.i(NewLogin.LOG_TAG, "Response is: $response")

                if (usuarioService.parseStatus(response) == "502"){
                    this.create(queue, usuario)
                } else{
                    dniLayout.error = "Este dni ya esta registrado"
                }
            },
            { error ->
                error.printStackTrace()
                //Log.e("UpdateUser", "Respuesta servidor: $error.networkResponse")
                //Log.e("UpdateUser", "No se pudieron guardar los cambios")
                //Snackbar.make(view, "El email no existe", Snackbar.LENGTH_LONG).show()
            }
        )
        queue.add(jsonRequest)
    }

    public fun create(queue: RequestQueue, usuario: Usuario){

        val jsonUsuario = JSONObject()
        jsonUsuario.put("username",usuario.username)
        jsonUsuario.put("nombre",usuario.nombre)
        jsonUsuario.put("apellido",usuario.apellido)
        jsonUsuario.put("correoElectronico",usuario.correoElectronico)
        jsonUsuario.put("contrasenia",usuario.contrasenia)
        jsonUsuario.put("dni",usuario.dni)
        jsonUsuario.put("fechaNacimiento",usuario.fechaNacimiento)


        val jsonRequest = JsonObjectRequest(urlBase, jsonUsuario,
            { response ->
                //Log.i(RegistroService.LOG_TAG, "Response is: $response")
                this.loginSuccessfull(usuario.username.toString(),usuario.nombre.toString())

                Toast.makeText(this, "Registro creado correctamente", Toast.LENGTH_SHORT).show()
            },
            { error ->
                error.printStackTrace()
                //Log.e(RegistroService.LOG_TAG, "No se pudo crear el registro")
                Toast.makeText(this, "No se pudo crear el registro", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonRequest)
    }



}


