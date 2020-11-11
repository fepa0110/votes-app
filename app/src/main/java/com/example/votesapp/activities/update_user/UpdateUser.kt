package com.example.votesapp.activities.update_user

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
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
import com.example.votesapp.maps.MapsActivity
import com.example.votesapp.model.Usuario
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONException
import org.json.JSONObject
import java.util.regex.Pattern

class UpdateUser : AppCompatActivity() {
    private var username : String? = null
    private var user : Usuario? = null
    private var usuarioService = NewLoginService()
    private val urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios"
    private var bundleMaps : Bundle? = null
    private var ubicacion : LatLng? = null

    @SuppressLint("SetTextI18n")
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

        val ubicacionTextView = findViewById<TextView>(R.id.latitud_data_textview_editar_perfil)

        nombreEditText.setText(intent.getStringExtra("param_nombre"))
        apellidoEditText.setText(intent.getStringExtra("param_apellido"))
        emailEditText.setText(intent.getStringExtra("param_correo"))
        passwordEditText.setText(intent.getStringExtra("param_contrasenia"))
        passwordEditText.setText(intent.getStringExtra("param_contrasenia"))

        ubicacionTextView.text = "(${intent.getDoubleExtra("param_latitude",0.0)}," +
                "${intent.getDoubleExtra("param_longitud",0.0)})"

        nombreEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence,i: Int,i1: Int,i2: Int) {
                validateNombre()
            }
            override fun afterTextChanged(editable: Editable) {}
        })

        apellidoEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence,i: Int,i1: Int,i2: Int) {
                validateApellido()
            }
            override fun afterTextChanged(editable: Editable) {}
        })

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence,i: Int,i1: Int,i2: Int) {
                validateEmail()
            }
            override fun afterTextChanged(editable: Editable) {}
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence,i: Int,i1: Int,i2: Int) {
                validatePassword()
            }
            override fun afterTextChanged(editable: Editable) {}
        })

        val ubicacionButton = findViewById<Button>(R.id.ubicacion_button_editar_perfil)
        ubicacionButton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent, 2)
        }

        val guardarButton = findViewById<Button>(R.id.button_guardar_editar_perfil)
        guardarButton.setOnClickListener {view ->
            if(validateNombre() && validateApellido() && validatePassword() && validateEmail()) {
                /*Snackbar.make(view, "Enviar update", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()*/
                val usuario = Usuario()
                usuario.username = intent.getStringExtra("param_username")
                usuario.nombre = nombreEditText.text.toString()
                usuario.apellido = apellidoEditText.text.toString()
                usuario.correoElectronico = emailEditText.text.toString()
                usuario.contrasenia = passwordEditText.text.toString()
                usuario.ubicacion = ubicacion

                this.isEmailExists(queue,usuario,view)
            }
        }

        val cancelarButton = findViewById<Button>(R.id.button_cancelar_editar_perfil)
        cancelarButton.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val ubicacionTextView = findViewById<TextView>(R.id.latitud_data_textview_editar_perfil)
        if (requestCode == 2) {
//            if (resultCode == Activity.RESULT_OK) {
                Log.i("UpdateUser", "resultCode: $resultCode")
                Log.i("UpdateUser", "Entre")

                val latitud = data?.getDoubleExtra("Latitud", 0.0)

                val longitud = data?.getDoubleExtra("Longitud",0.0)

                ubicacion = LatLng(latitud!!.toDouble(),longitud!!.toDouble())

                Log.i("UpdateUser", "$ubicacion")

//                Toast.makeText(this, "$ubicacion", Toast.LENGTH_SHORT).show()
                ubicacionTextView.text = "(${ubicacion?.latitude},${ubicacion?.longitude})"
//            }
        }
    }
    private fun validateNombre() : Boolean{
        val nombreEditTextLayout = findViewById<EditText>(R.id.nombre_layout_editarPerfil) as TextInputLayout
        val nombreEditText = findViewById<EditText>(R.id.nombre_editText_editar_perfil)
        if(nombreEditText.text.toString() == user?.nombre) {
            nombreEditTextLayout.error = "Ya tienes ese nombre"
            return false
        }
        if(nombreEditText.text.isBlank() or nombreEditText.text.isEmpty()) {
            nombreEditTextLayout.error =  "El nombre no puede estar vacio"
            return false
        }
        return true
    }

    private fun validateApellido() : Boolean{
        val apellidoEditTextLayout = findViewById<EditText>(R.id.apellido_layout_editarPerfil) as TextInputLayout
        val apellidoEditText = findViewById<EditText>(R.id.apellido_editText_editar_perfil)
        if(apellidoEditText.text.toString() == user?.apellido) {
            apellidoEditTextLayout.error = "Ya tienes ese apellido"
            return false
        }
        if(apellidoEditText.text.isBlank() or apellidoEditText.text.isEmpty()) {
            apellidoEditTextLayout.error =  "El apellido no puede estar vacio"
            return false
        }
        return true
    }

    private fun validatePassword() : Boolean{
        val passwordEditTextLayout = findViewById<EditText>(R.id.contraseña_layout_editarPerfil) as TextInputLayout
        val passwordEditText = findViewById<EditText>(R.id.contraseña_editText_editar_perfil)
        val passwordPattern = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$")

        if (!passwordPattern.matcher(passwordEditText.text).matches()) {
            passwordEditTextLayout.error =
                "La contraseña debe tener entre 8 y 16 caracteres, al menos un digito, al menos una minuscula y al menos una mayuscula"
            return false
        }
        if(passwordEditText.text.toString() == user?.contrasenia) {
            passwordEditTextLayout.error = "Ya tienes esa contrasenia"
            return false
        }
        if(passwordEditText.text.isEmpty()) {
            passwordEditTextLayout.error = "La contraseña no puede estar vacia"
            return false
        }
        if(passwordEditText.text.isBlank()) {
            passwordEditTextLayout.error = "La contraseña no puede contener espacios"
            return false
        }

        return true
    }

    private fun validateEmail() : Boolean{
        val emailEditText = findViewById<EditText>(R.id.email_editText_editar_perfil)
        val emailEditTextLayout = findViewById<EditText>(R.id.email_layout_editarPerfil) as TextInputLayout
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text).matches()) {
            emailEditTextLayout.error = "Correo electrónico inválido"
            return false
        }
        if(emailEditText.text.toString() == user?.correoElectronico) {
            emailEditTextLayout.error = "Ya tienes ese email"
            return false
        }
        return true
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
        if(usuario.ubicacion != null) {
            val jsonObjectUbicacion = JSONObject()
            jsonObjectUbicacion.put("latitude",usuario.ubicacion!!.latitude)
            jsonObjectUbicacion.put("longitude",usuario.ubicacion!!.longitude)

            jsonUsuario.put("ubicacion", jsonObjectUbicacion)
        }

        Log.i(NewLogin.LOG_TAG, "Usuario para enviar: ${jsonUsuario.toString()}")

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
        jsonUsuario.put("username", usuario.username)
        jsonUsuario.put("correoElectronico", usuario.correoElectronico)

        val emailEditText = findViewById<EditText>(R.id.email_editText_editar_perfil)

        val jsonRequest = JsonObjectRequest(
            "$urlBase/email", jsonUsuario,
            { response ->
                Log.i(NewLogin.LOG_TAG, "Response is: $response")

                if (usuarioService.parseStatus(response) == "502"){
                    Log.i("UpdateUser","email response: $response")
                    this.sendResponse(queue,usuario,view)
                } else{
                    emailEditText.error = "Este email ya esta registrado"
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

    private fun updateSuccessfull(){
        Toast.makeText(this, "Se guardaron los cambios correctamente", Toast.LENGTH_SHORT).show()
        val handler = Handler()
        handler.postDelayed({onBackPressed()}, 500)
    }
}