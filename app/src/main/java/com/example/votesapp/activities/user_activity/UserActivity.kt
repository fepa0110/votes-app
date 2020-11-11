package com.example.votesapp.activities.user_activity

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.R
import com.example.votesapp.activities.newLogin.NewLogin
import com.example.votesapp.activities.update_user.UpdateUser
import com.example.votesapp.maps.MapsActivity
import com.example.votesapp.model.Usuario
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject

class UserActivity : Fragment() {
    private var username : String? = null
    private var user : Usuario? = null
    private lateinit var fechaNacimientoTextView : TextView
    private lateinit var emailTextView : TextView
    private lateinit var dniTextView : TextView
    private lateinit var apellidoTextView : TextView
    private lateinit var nombreTextView : TextView
    private lateinit var usernameTextView : TextView
    private lateinit var ubicacionTextView: TextView

    companion object {
        fun newInstance(): UserActivity = UserActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewFragment = inflater.inflate(R.layout.user_activity, container, false)

        username = this.activity?.intent?.getStringExtra("param_username")

        usernameTextView = viewFragment.findViewById<TextView>(R.id.user_activity_username)
        nombreTextView = viewFragment.findViewById<TextView>(R.id.userActivity_nombre)
        apellidoTextView = viewFragment.findViewById<TextView>(R.id.userActivity_apellido)
        dniTextView = viewFragment.findViewById<TextView>(R.id.userActivity_dni)
        emailTextView = viewFragment.findViewById<TextView>(R.id.userActivity_email)
        fechaNacimientoTextView = viewFragment.findViewById<TextView>(R.id.userActivity_fechaNacimiento)
        ubicacionTextView = viewFragment.findViewById<TextView>(R.id.ubicacion_data_textview_user_activity)


        val editarPerfilButton = viewFragment.findViewById<Button>(R.id.editar_perfil_button_userActivity)
        editarPerfilButton.setOnClickListener{
            val intent = Intent(viewFragment.context, UpdateUser::class.java)
            intent.putExtra("param_username", user?.username)
            intent.putExtra("param_nombre", user?.nombre)
            intent.putExtra("param_apellido", user?.apellido)
            intent.putExtra("param_contrasenia", user?.contrasenia)
            intent.putExtra("param_correo", user?.correoElectronico)
            intent.putExtra("param_fechaNacimiento", user?.fechaNacimiento)
            intent.putExtra("param_latitude", user?.ubicacion?.latitude)
            intent.putExtra("param_longitud", user?.ubicacion?.longitude)
            startActivity(intent)
        }

        val logoutButton = viewFragment.findViewById<Button>(R.id.logout_button_userActivity)
        logoutButton.setOnClickListener{
            Snackbar.make(viewFragment, "Vuelva pronto ${user?.nombre}", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            this.activity?.finish()
            val intent = Intent(this.context, NewLogin::class.java)
            startActivity(intent)
        }

        this.getUsernameData()

        return viewFragment
    }

    private fun getUsernameData(){
        val urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios"

        val requestQueue: RequestQueue = Volley.newRequestQueue(context)

        //NUeva peticion JsonObject
        val response = JsonObjectRequest(
            Request.Method.GET, "$urlBase/$username", null,
            { response ->
                user = parseUsuarioJson(response)
                setUserInfo()
            })
        { error -> Log.d("Usuario", "Error Respuesta en Json: " + error.message)
        }

        // Añadir peticion a la cola
        requestQueue.add(response)
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
            usuario.fechaNacimiento = usuarioObject.getString("fechaNacimiento")
            usuario.contrasenia = usuarioObject.getString("contrasenia")
            val ubicacion = usuarioObject.getJSONObject("ubicacion")
            usuario.ubicacion = LatLng(ubicacion.getDouble("latitude"),ubicacion.getDouble("longitude"))
            if(usuario.fechaNacimiento == "null") usuario.fechaNacimiento = ""

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

    private fun setUserInfo(){
        usernameTextView.text = user?.username
        nombreTextView.text = user?.nombre
        apellidoTextView.text = user?.apellido
        dniTextView.text = user?.dni
        emailTextView.text = user?.correoElectronico
        fechaNacimientoTextView.text = user?.fechaNacimiento
        val textUbicacion = "(${user?.ubicacion?.latitude},${user?.ubicacion?.longitude})"
        ubicacionTextView.text = textUbicacion
    }

    override fun onResume() {
        super.onResume()
        this.getUsernameData()
    }
}