package com.example.votesapp.activities.registro_usuario

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.R
import com.example.votesapp.activities.newLogin.NewLogin
import com.example.votesapp.activities.newLogin.NewLoginService
import com.example.votesapp.model.Usuario
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_registro_usuario.*
import org.json.JSONObject
import java.util.regex.Pattern

class Registro_usuario : AppCompatActivity() {
    private val urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios"

    var siguiente: Button? = null
    var volver: Button? = null
    //Edit
    var userNameUsuario: TextInputEditText? = null
    var correoUsuario: TextInputEditText? = null
    var contraUsuario: TextInputEditText? = null

    //Layout
    var registroUserName: TextInputLayout? = null
    var registroCorreo: TextInputLayout? = null
    var registroContra: TextInputLayout? = null
    var regitroConfirmarContra:TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        registroUserName =
            findViewById<View>(R.id.registro_userName) as TextInputLayout
        registroCorreo =
            findViewById<View>(R.id.registro_correoElectronico) as TextInputLayout
        registroContra =
            findViewById<View>(R.id.registro_contraseñaUsuario) as TextInputLayout
        regitroConfirmarContra =
            findViewById<View>(R.id.confirmarRegistroContra) as TextInputLayout

        //traigo los componentes de la pantalla
        val userNameUsuario = findViewById<TextInputEditText>(R.id.text_registro_userName)
       correoUsuario = findViewById<View>(R.id.text_registro_correoElectronico) as TextInputEditText
        val contraUsuario = findViewById<TextInputEditText>(R.id.text_registroContraseña)
        val confirmarContra = findViewById<TextInputEditText>(R.id.text_confirmarRegistroContra);

        //confirmarContraUsuario = (EditText)findViewById(R.id.confirmarContraseñaUsuario);

        siguiente = findViewById<View>(R.id.botonSiguiente) as Button
        volver = findViewById<View>(R.id.botonVolver) as Button

        userNameUsuario.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int,count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, star: Int,before: Int,count: Int) {
                registroUserName!!.error = null
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        correoUsuario!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence,start: Int,count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int,before: Int,count: Int) {
                esCorreoValido(charSequence.toString())}

            override fun afterTextChanged(editable: Editable) {}
        })
        contraUsuario.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence,i: Int,i1: Int,i2: Int) {
                escontraseñaValida(charSequence.toString())
            }
            override fun afterTextChanged(editable: Editable) {}
        })

        confirmarContra.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence,i: Int,i1: Int,i2: Int) {
                esConfirmarContraseñaValida(charSequence.toString())
            }
            override fun afterTextChanged(editable: Editable) {}
        })

        //Al hacer click en el boton volver
        volver!!.setOnClickListener {
            val volver = Intent(this@Registro_usuario, NewLogin::class.java)
            startActivity(volver)
            finish()
        }

        //Al hacer click en el boton siguiente
        siguiente!!.setOnClickListener {
            validarDatos() }
    }

    //Validar el nombre de usuario
    private fun esNombreValido(nombre: String): Boolean {
        val patron =
            Pattern.compile("^[a-zA-Z0-9](_(?!(\\.|_))|\\.(?!(_|\\.))|[a-zA-Z0-9]){6,18}[a-zA-Z0-9]$")
        if (!patron.matcher(nombre).matches() || nombre.length > 30) {
            registroUserName!!.error = "Nombre inválido"
            return false
        } else {
            registroUserName!!.error = null
        }
        return true
    }

    //Validar correo electronico
    private fun esCorreoValido(correo: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            registroCorreo!!.error = "Correo electrónico inválido"
            return false
        } else {
            registroCorreo!!.error = null
        }
        return true
    }

    //Validar la contraseña
    private fun escontraseñaValida(contraseña: String): Boolean {
        val patron =
            Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$")
        if (!patron.matcher(contraseña).matches()) {
            registroContra!!.error =
                "La contraseña debe tener entre 8 y 16 caracteres, al menos un digito, al menos una minuscula y al menos una mayuscula"
            return false
        } else {
            registroContra!!.error = null
        }
        return true
    }
    //Validar la confirmacion de la contraseña
    private fun esConfirmarContraseñaValida(confirmar: String): Boolean {
        //Pattern patron = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$");
        val y = registroContra!!.editText!!.text.toString()
        if (confirmar.equals(y)) {
            regitroConfirmarContra!!.error = null
            return true
        } else {
            regitroConfirmarContra!!.error = "Las contraseña no son iguales, vuelva a intentarlo"
        }
        return false
    }

    private fun validarDatos() {
        val nombre = registroUserName!!.editText!!.text.toString()
        val correo = registroCorreo!!.editText!!.text.toString()
        val contraseña = registroContra!!.editText!!.text.toString()
        val confirmar = regitroConfirmarContra!!.editText!!.text.toString()

        val a = esNombreValido(nombre)
        val c = esCorreoValido(correo)
        val b = escontraseñaValida(contraseña)
        val d = esConfirmarContraseñaValida(confirmar)

        if (a && b && c && d) {
            val usuario = Usuario()
            usuario.username = text_registro_userName.text.toString()
            usuario.correoElectronico=correoUsuario!!.text.toString()
            usuario.contrasenia = text_registroContraseña.text.toString()
           this.isEmailExist(usuario)
        }
    }
    private fun isUserNameExists( usuario : Usuario) {
        val queue = Volley.newRequestQueue(this)
        val usuarioService = NewLoginService()
        val jsonUsuario = JSONObject()
        jsonUsuario.put("username", usuario.username)

        val registroUserName = findViewById<TextInputLayout>(R.id.registro_userName)


        val jsonRequest = JsonObjectRequest(
            "$urlBase/username", jsonUsuario,
            { response ->
                Log.i(NewLogin.LOG_TAG, "Response is: $response")

                if (usuarioService.parseStatus(response) == "502"){
                    isEmailExist(usuario)
                } else{
                    registroUserName.error = "Este nombre de usuario ya esta registrado"
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

    private fun isEmailExist( usuario : Usuario) {
        val queue = Volley.newRequestQueue(this)
        val usuarioService = NewLoginService()
        val jsonUsuario = JSONObject()
        jsonUsuario.put("correoElectronico", usuario.correoElectronico)


        val registroCorreo=findViewById<TextInputLayout>(R.id.registro_correoElectronico);

        val jsonRequest = JsonObjectRequest(
            "$urlBase/email", jsonUsuario,
            { response ->
                Log.i(NewLogin.LOG_TAG, "Response is: $response")

                if (usuarioService.parseStatus(response) == "502"){
                    val siguiente = Intent(this@Registro_usuario, Registro_usuario2::class.java)
                    siguiente.putExtra("userName", text_registro_userName.text.toString())
                    siguiente.putExtra("correo", text_registro_correoElectronico.text.toString())
                    siguiente.putExtra("contrasenia", text_registroContraseña.text.toString())
                    startActivity(siguiente)

                } else{
                    registroCorreo.error = "Este Correo Electronico ya esta registrado"
                }
            },
            { error ->
                error.printStackTrace()
                Log.e("UpdateUser", "Respuesta servidor: $error.networkResponse")
                //Log.e("UpdateUser", "No se pudieron guardar los cambios")
                //Snackbar.make(view, "El email no existe", Snackbar.LENGTH_LONG).show()
            }
        )
        queue.add(jsonRequest)
    }
}