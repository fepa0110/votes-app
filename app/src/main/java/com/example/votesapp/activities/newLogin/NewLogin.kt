package com.example.votesapp.activities.newLogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.R
import com.example.votesapp.activities.mainActivity.MainActivity
import com.example.votesapp.activities.registro_usuario.Registro_usuario
import com.example.votesapp.model.Usuario
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class NewLogin : AppCompatActivity() {
    private val url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios"
    //private var infoUser : LoggedInUser? = null
    //private var userLogin : Usuario? = null
    private var usuarioService = NewLoginService()

    companion object {
        const val LOG_TAG = "NewLoginService"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_login)

        val queue = Volley.newRequestQueue(this)
        val userNameEditText = findViewById<EditText>(R.id.newLogin_username)
        val passwordEditText = findViewById<EditText>(R.id.newLogin_password)
        val loginButton = findViewById<Button>(R.id.btn_login)
        val registerButton = findViewById<Button>(R.id.btn_registrarse)

        loginButton.setOnClickListener(View.OnClickListener {view ->
            val usuario = Usuario()
            usuario.username = userNameEditText.text.toString()
            usuario.contrasenia = passwordEditText.text.toString()
            if (usuario.username!!.isEmpty()){
                userNameEditText.error = "El campo no puede estar vacio";
            }
            if (usuario.contrasenia!!.isEmpty()){
                passwordEditText.error = "El campo no puede estar vacio";
            }
            if (usuario.username!!.isNotEmpty() && usuario.contrasenia!!.isNotEmpty()) {
                this.sendResponse(queue, usuario, view)
            }
        })

        registerButton.setOnClickListener { view ->
            val intent = Intent(this, Registro_usuario::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun sendResponse(queue: RequestQueue, usuario: Usuario, view: View) {
        val jsonUsuarioLogin = JSONObject()
        jsonUsuarioLogin.put("username", usuario.username)
        jsonUsuarioLogin.put("contrasenia", usuario.contrasenia)

        val jsonRequest = JsonObjectRequest("$url/login/", jsonUsuarioLogin,
            { response ->
                Log.i(LOG_TAG, "Response is: $response")

                if (usuarioService.parseStatus(response) == "200"){
                    val userLogin = usuarioService.parseUsuarioJson(response)
                    loginSuccessfull(userLogin)
                } else{
                    Snackbar.make(view, "Usuario o contraseña no validos", Snackbar.LENGTH_LONG).show()
                }

            },
            { error ->
                error.printStackTrace()
                Log.e(LOG_TAG, "Respuesta servidor: $error.networkResponse")
                Log.e(LOG_TAG, "No se pudo loguear correctamente")
                Snackbar.make(view, "No se pudo loguear correctamente", Snackbar.LENGTH_LONG).show()
            }
        )
        queue.add(jsonRequest)
    }

    private fun loginSuccessfull(user: Usuario){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("param_username",user.username)
        intent.putExtra("param_usuario_nombre",user.nombre)
        startActivity(intent)
        finish()
    }
}