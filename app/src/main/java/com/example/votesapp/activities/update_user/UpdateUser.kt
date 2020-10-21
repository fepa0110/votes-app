package com.example.votesapp.activities.update_user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.R
import com.example.votesapp.activities.newLogin.NewLogin
import com.example.votesapp.activities.newLogin.NewLoginService
import com.example.votesapp.model.Usuario
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject

class UpdateUser : AppCompatActivity() {
    private var username : String? = null
    private var user : Usuario? = null
    private var usuarioService = NewLoginService()
    private val urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        val queue = Volley.newRequestQueue(this)

        username = intent.getStringExtra("param_username")
        //this.getUsernameData(queue)

        val nombreEditText = findViewById<EditText>(R.id.nombre_editText_editar_perfil)
        val apellidoEditText = findViewById<EditText>(R.id.apellido_editText_editar_perfil)
        val emailEditText = findViewById<EditText>(R.id.email_editText_editar_perfil)
        val passwordEditText = findViewById<EditText>(R.id.contraseña_editText_editar_perfil)


        nombreEditText.setText(intent.getStringExtra("param_nombre"))
        apellidoEditText.setText(intent.getStringExtra("param_apellido"))
        emailEditText.setText(intent.getStringExtra("param_correo"))
        passwordEditText.setText(intent.getStringExtra("param_contrasenia"))
        Log.i("Contraseña: ",intent.getStringExtra("param_contrasenia"))


        val guardarButton = findViewById<Button>(R.id.button_guardar_editar_perfil)
        guardarButton.setOnClickListener {view ->
            if(validateNombre() && validateApellido() && validatePassword() && validateEmail()) {
                /*Snackbar.make(view, "Enviar update", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()*/
                val usuario = Usuario()
                usuario.username = user?.username
                usuario.nombre = nombreEditText.text.toString()
                usuario.apellido = apellidoEditText.text.toString()
                usuario.correoElectronico = emailEditText.text.toString()
                usuario.contrasenia = passwordEditText.text.toString()

                this.isEmailExists(queue,usuario,view)
                //this.sendResponse(queue,usuario,view)
            }
        }

        val cancelarButton = findViewById<Button>(R.id.button_cancelar_editar_perfil)
        cancelarButton.setOnClickListener {
            finish()
        }
    }

    private fun validateNombre() : Boolean{
        val nombreEditText = findViewById<EditText>(R.id.nombre_editText_editar_perfil)
        if(nombreEditText.text.toString() == user?.nombre) {
            nombreEditText.error = "Ya tienes ese nombre"
            return false
        }
        return true
    }

    private fun validateApellido() : Boolean{
        val apellidoEditText = findViewById<EditText>(R.id.apellido_editText_editar_perfil)
        if(apellidoEditText.text.toString() == user?.apellido) {
            apellidoEditText.error = "Ya tienes ese apellido"
            return false
        }
        return true
    }

    private fun validatePassword() : Boolean{
        val passwordEditText = findViewById<EditText>(R.id.contraseña_editText_editar_perfil)
        if(passwordEditText.text.toString() == user?.contrasenia) {
            passwordEditText.error = "Ya tienes esa contrasenia"
            return false
        }
        return true
    }

    private fun validateEmail() : Boolean{
        val emailEditText = findViewById<EditText>(R.id.email_editText_editar_perfil)
        if(emailEditText.text.toString() == user?.correoElectronico) {
            emailEditText.error = "Ya tienes ese email"
            return false
        }
        return true
    }

    private fun getUsernameData(queue: RequestQueue){
        //NUeva peticion JsonObject
        val response = JsonObjectRequest(
            Request.Method.GET, "$urlBase/$username", null,
            { response ->
                user = parseUsuarioJson(response)
            })
        { error -> Log.d("Usuario", "Error Respuesta en Json: " + error.message)
        }
        // Añadir peticion a la cola
        queue.add(response)
    }

    private fun parseUsuarioJson(jsonObject: JSONObject): Usuario {
        //Variables Locales
        val usuario = Usuario()
        //lateinit var loggedInUser: LoggedInUser

        val usuarioObject: JSONObject?

        try {
            usuarioObject = jsonObject.getJSONObject("data")

            usuario.username = usuarioObject.getString("username")
            usuario.nombre = usuarioObject.getString("nombre")
            usuario.apellido = usuarioObject.getString("apellido")
            usuario.dni = usuarioObject.getString("dni")
            usuario.correoElectronico = usuarioObject.getString("correoElectronico")
            usuario.contrasenia = usuarioObject.getString("contrasenia")

            //LoggedInUser(usuarioObject.getString("username"), usuarioObject.getString("nombre"))
            //usuario.username = usuarioObject.getString("username")
            //usuario.nombre = usuarioObject.getString("nombre")
            Log.i("UserActivity", "Usuario parseado: $usuario")
            //Log.i(LOG_TAG, "Usuario parseado: $loggedInUser")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return usuario
    }

    private fun sendResponse(queue: RequestQueue, usuario: Usuario, view: View) {
        val jsonUsuario = JSONObject()
        jsonUsuario.put("username", usuario.username)
        if(usuario.nombre != null && usuario.nombre!!.isNotEmpty()) {
            jsonUsuario.put("nombre", usuario.nombre)
        }
        if(usuario.apellido != null && usuario.apellido!!.isNotEmpty()) {
            jsonUsuario.put("apellido", usuario.apellido)
        }
        if(usuario.contrasenia != null && usuario.contrasenia!!.isNotEmpty()) {
            jsonUsuario.put("contrasenia", usuario.contrasenia)
        }
        if(usuario.correoElectronico != null && usuario.correoElectronico!!.isNotEmpty()) {
            jsonUsuario.put("correoElectronico", usuario.correoElectronico)
        }

        val jsonRequest = JsonObjectRequest(
            "$urlBase/edit/", jsonUsuario,
            { response ->
                Log.i(NewLogin.LOG_TAG, "Response is: $response")
                this.updateSuccessfull()
            },
            { error ->
                error.printStackTrace()
                Log.e("UpdateUser", "Respuesta servidor: $error.networkResponse")
                Log.e("UpdateUser", "No se pudieron guardar los cambios")
                Snackbar.make(view, "No se pudieron guardar los cambios", Snackbar.LENGTH_LONG).show()
            }
        )
        queue.add(jsonRequest)
    }

    private fun isEmailExists(queue: RequestQueue, usuario : Usuario, view: View) {
        val jsonUsuario = JSONObject()
        jsonUsuario.put("correoElectronico", usuario.correoElectronico)

        val emailEditText = findViewById<EditText>(R.id.email_editText_editar_perfil)

        val jsonRequest = JsonObjectRequest(
            "$urlBase/email", jsonUsuario,
            { response ->
                Log.i(NewLogin.LOG_TAG, "Response is: $response")

                if (usuarioService.parseStatus(response) == "502"){
                    this.sendResponse(queue,usuario,view)
                } else{
                    emailEditText.error = "Este email ya esta registrado"
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

    private fun updateSuccessfull(){
        Toast.makeText(this, "Se guardaron los cambios correctamente", Toast.LENGTH_SHORT).show()
        finish()
    }
}