package com.example.votesapp.activities.userByComprension

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.R
import com.example.votesapp.activities.votante_by_user.VotanteByUsernameAdapter
import com.example.votesapp.model.Usuario
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class FilterUserByComprension : Fragment() {
    private var usuariosListView: ListView? = null

    //Llamamos el adapter
    private var filterByUserComprensionAdapter: UserByComprensionAdapter? = null

    private var searchUsuariosList: List<String>? = ArrayList<String>()

    companion object {
        fun newInstance(): FilterUserByComprension = FilterUserByComprension()
    }

    private var salaId : Int? = null
    private var usernameOwner : String? = null
    private var estadoSala : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewFragment = inflater.inflate(R.layout.activity_filter_user_by_comprension, container, false)

        val desdeNombreEditText = viewFragment.findViewById<EditText>(R.id.nombre_desde_editText_user_comprension)
        val hastaNombreEditText = viewFragment.findViewById<EditText>(R.id.nombre_hasta_editText_user_comprension)

        val desdeApellidoEditText = viewFragment.findViewById<EditText>(R.id.apellido_desde_editText_user_comprension)
        val hastaApellidoEditText = viewFragment.findViewById<EditText>(R.id.apellido_hasta_editText_user_comprension)

        val previsualizarButton = viewFragment.findViewById<Button>(R.id.previsualizar_user_comprension_button)
        val clearButton = viewFragment.findViewById<Button>(R.id.clear_user_comprension_button)
        val finalizarButton = viewFragment.findViewById<Button>(R.id.user_comprension_button_finalizar)

        this.salaId = arguments?.getInt("param_id")
        this.usernameOwner = arguments?.getString("param_username")
        this.estadoSala = arguments?.getString("param_estado")

        usuariosListView = viewFragment.findViewById(R.id.user_comprension_username_listView)

        //Inicializo el adapter y le paso id de sala
        filterByUserComprensionAdapter = UserByComprensionAdapter(viewFragment.context,salaId!!)

        if(estadoSala == "FINALIZADA" || estadoSala == "DISPONIBLE"){
            previsualizarButton.isEnabled = false
            previsualizarButton.backgroundTintList =  resources.getColorStateList(R.color.gris, null)

            clearButton.isEnabled = false
            clearButton.backgroundTintList =  resources.getColorStateList(R.color.gris, null)

            finalizarButton.isEnabled = false
            finalizarButton.setBackgroundColor(resources.getColor(R.color.gris, null))

        }

        //Crear adaptador y setear
        usuariosListView?.adapter = filterByUserComprensionAdapter

        previsualizarButton.setOnClickListener { view ->
            val desdeNombre = desdeNombreEditText.text.toString()
            val hastaNombre = hastaNombreEditText.text.toString()

            val desdeApellido = desdeApellidoEditText.text.toString()
            val hastaApellido = hastaApellidoEditText.text.toString()

            getUsuarios(view,desdeNombre, hastaNombre, desdeApellido, hastaApellido)
        }
        clearButton.setOnClickListener { view ->
            filterByUserComprensionAdapter!!.clearList()
        }

        finalizarButton.setOnClickListener {
            filterByUserComprensionAdapter!!.sendVotantes()
        }

        return viewFragment
    }

    private fun getUsuarios(view: View,desdeNombre: String, hastaNombre: String, desdeApellido: String, hastaApellido: String) {
        val url =
            "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios/filterByComprension"

        val requestQueue= Volley.newRequestQueue(context)
        //Gestionar peticion del archivo JSON

        //Crear nueva cola de peticiones

        val jsonUsuarios = JSONObject()

        jsonUsuarios.put("desdeNombre",desdeNombre)
        jsonUsuarios.put("hastaNombre",hastaNombre)
        jsonUsuarios.put("desdeApellido",desdeApellido)
        jsonUsuarios.put("hastaApellido",hastaApellido)

        val jsonArrayRequest = JsonObjectRequest(
            Request.Method.POST,url, jsonUsuarios,
            { response ->
                Log.i("FilterUserByComprension", "Response is: $response")
                val listUsuarios = parseListUsuariosJson(response)
                filterByUserComprensionAdapter?.setListUsuarios(listUsuarios)
            },
            { error ->
                Toast.makeText(context, "Error al recibir usuarios", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
//                Log.e(SalaService.LOG_TAG, "No se pudo crear la sala")
//                Toast.makeText(context, "No se pudo crear la sala", Toast.LENGTH_SHORT).show()
            }
        )

        // Añadir peticion a la cola
        requestQueue.add(jsonArrayRequest)
    }

    private fun parseListUsuariosJson(jsonObject: JSONObject): MutableList<Usuario?> {
        //Variables Locales
        val usuarios: MutableList<Usuario?> = ArrayList<Usuario?>()
        var jsonArray: JSONArray? = null
        try {
            //Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                try {
                    val objeto = jsonArray.getJSONObject(i)

                    val usuario = Usuario()
                    usuario.username = objeto.getString("username")
                    usuario.nombre = objeto.getString("nombre")
                    usuario.apellido = objeto.getString("apellido")

                    usuarios.add(usuario)
//                    Log.i("AddVotanteByUser", "Usuario añadido")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return usuarios
    }
}