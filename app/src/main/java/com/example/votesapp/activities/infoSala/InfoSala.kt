package com.example.votesapp.activities.infoSala

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.R
import com.example.votesapp.R.*
import com.example.votesapp.activities.newLogin.NewLogin
import com.example.votesapp.activities.update_user.UpdateUser
import com.example.votesapp.model.Sala
import com.example.votesapp.model.Usuario
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.toolbar_menu_mis_salas.*
import org.json.JSONException
import org.json.JSONObject

class InfoSala : Fragment() {
    private var salaId : Int? = null
    private var sala : Sala? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewFragment = inflater.inflate(layout.info_sala, container, false)

        salaId = this.activity?.intent?.getIntExtra("param_id",0)

        this.getSalaData(viewFragment)

        return viewFragment
    }

    companion object {
        fun newInstance(): InfoSala = InfoSala()
    }

    private fun getSalaData(viewFragment: View){
        val urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas"

        val requestQueue: RequestQueue = Volley.newRequestQueue(context)

        //NUeva peticion JsonObject
        val response = JsonObjectRequest(
            Request.Method.GET, "$urlBase/$salaId", null,
            { response ->
                sala = parseSalaJson(response)
                setSalaInfo(viewFragment)
            })
        { error -> Log.d("InfoSala", "Error Respuesta en Json: " + error.message)
        }

        // Añadir peticion a la cola
        requestQueue.add(response)
    }

    private fun parseSalaJson(jsonObject: JSONObject): Sala {
        //Variables Locales
        val sala = Sala()
        //lateinit var loggedInUser: LoggedInUser

        val salaObject: JSONObject?

        try {
            salaObject = jsonObject.getJSONObject("data")

            sala.nombre = salaObject.getString("nombre")
            sala.username = salaObject.getJSONObject("usuario").getString("username")
            sala.estado = salaObject.getString("estado")

            Log.i("InfoSala", "Sala parseada: $sala")
            //Log.i(LOG_TAG, "Usuario parseado: $loggedInUser")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return sala
    }

    private fun setSalaInfo(viewFragment: View){

        val nombreTextView = viewFragment.findViewById<TextView>(R.id.nombre_text_infoSala)
        nombreTextView.text = sala?.nombre

        val usernameTextView = viewFragment.findViewById<TextView>(R.id.usuario_text_infoSala)
        usernameTextView.text = sala?.username

        val estadoTextView = viewFragment.findViewById<TextView>(R.id.estado_text_infoSala)
        if(sala?.estado == "PENDIENTE") {
            estadoTextView.setTextColor(Color.parseColor("#AA0043C9"))
        }
        else if (sala?.estado == "DISPONIBLE")
        {
            estadoTextView.setTextColor(Color.parseColor("#4CAF50"))
        }
        else if (sala?.estado == "FINALIZADA") {
            estadoTextView.setTextColor(Color.parseColor("#AA4301"))
        }

        estadoTextView.text = sala?.estado
    }

    private fun habilitarSala(viewFragment: View){



    }

}