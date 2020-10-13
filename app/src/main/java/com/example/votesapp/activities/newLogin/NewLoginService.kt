package com.example.votesapp.activities.newLogin

import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.example.votesapp.model.Usuario
import com.example.votesapp.services.UsuarioService
import org.json.JSONException
import org.json.JSONObject

class NewLoginService {
    companion object {
        const val LOG_TAG = "NewLoginService"
    }

    //parseo el Json
    fun parseUsuarioJson(jsonObject: JSONObject): Usuario {
        //Variables Locales
        val usuario = Usuario()
        //lateinit var loggedInUser: LoggedInUser

        val usuarioObject: JSONObject?

        try {
            usuarioObject = jsonObject.getJSONObject("data")

            usuario.username = usuarioObject.getString("username")
            usuario.nombre = usuarioObject.getString("nombre")

            //LoggedInUser(usuarioObject.getString("username"), usuarioObject.getString("nombre"))
            //usuario.username = usuarioObject.getString("username")
            //usuario.nombre = usuarioObject.getString("nombre")
            Log.i(LOG_TAG, "Usuario parseado: $usuario")
            //Log.i(LOG_TAG, "Usuario parseado: $loggedInUser")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return usuario
    }

    fun parseStatus(jsonObject: JSONObject): String? {
        //Variables Locales
        var status : String? = ""

        try {
            status = jsonObject.getString("StatusCode")

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return status
    }

}