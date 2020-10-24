package com.example.votesapp.activities.votante_by_user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.R
import com.example.votesapp.activities.crear_sala.CrearSala
import com.example.votesapp.activities.mis_salas.MisSalas
import com.example.votesapp.activities.mis_salas.Sala
import com.example.votesapp.activities.mis_salas.SalaAdapter
import com.example.votesapp.model.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class AddVotanteByUser : Fragment() {
    //Atributos
    private var usuariosListView: ListView? = null

    //Llamamos el adapter
    private var votanteByUsernameAdapter: VotanteByUsernameAdapter? = null

    private var autoCompleteTextView: AutoCompleteTextView? = null
    private var autoCompleteAdapter: ArrayAdapter<String>? = null

    private var searchUsuariosList: List<String>? = ArrayList<String>()

    companion object {
        fun newInstance(): AddVotanteByUser = AddVotanteByUser()
    }

    private var salaId : Int? = null
    private var usernameOwner : String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewFragment = inflater.inflate(R.layout.activity_add_votante_by_user, container, false)

        this.salaId = arguments?.getInt("param_id")
        this.usernameOwner = arguments?.getString("param_username")

        usuariosListView = viewFragment.findViewById(R.id.add_votante_username_listView)

        //Inicializo el adapter y le paso id de sala
        votanteByUsernameAdapter = VotanteByUsernameAdapter(viewFragment.context,salaId!!)

        //Busco todos los usuarios registrados
        this.getAllUsuarios(viewFragment)

        //Crear adaptador y setear
        usuariosListView?.adapter = votanteByUsernameAdapter

        val agregarVotanteButton = viewFragment.findViewById<FloatingActionButton>(R.id.add_votante_button)
        agregarVotanteButton.setOnClickListener {view ->
            val agregarVotanteText = autoCompleteTextView?.text.toString()

            //Si el texto ingresa no esta vacio o en blanco
            if(agregarVotanteText.isNotEmpty() && agregarVotanteText.isNotBlank()){
                if(agregarVotanteText.toUpperCase(Locale.ROOT) != usernameOwner?.toUpperCase(Locale.ROOT)){
                    val usuario = Usuario(agregarVotanteText)
                    if(!votanteByUsernameAdapter!!.containUser(usuario)){
                        isUserExist(view, usuario)
                    }
                    else {
                        Snackbar.make(view, "Ese usuario ya esta agregado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }
                }
                else Snackbar.make(view, "No puedes agregarte a tu misma sala", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()
            }
            else{
                autoCompleteTextView?.error = "Por favor ingrese un usuario"
                autoCompleteTextView?.text?.clear()
            }
        }

        val finalizarButton = viewFragment.findViewById<Button>(R.id.add_votante_button_finalizar)
        finalizarButton.setOnClickListener {
            votanteByUsernameAdapter!!.sendVotantes()
        }

        return viewFragment
    }

    private fun isUserExist(view: View, usuario: Usuario){
            val urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios"

            val requestQueue: RequestQueue = Volley.newRequestQueue(context)

            val username = usuario.username
            //NUeva peticion JsonObject
            val response = JsonObjectRequest(
                Request.Method.GET, "$urlBase/$username", null,
                { response ->
                    if(parseUsuarioJson(response) == usuario){
                        votanteByUsernameAdapter!!.addUser(usuario)
                        autoCompleteTextView?.text?.clear() //Limpio el texto del editText
                        // Toast.makeText(view.context, "Usuario $username existe", Toast.LENGTH_SHORT).show()
                    }
                    else autoCompleteTextView?.error = "Este usuario no existe"
                })
            { error ->
                Log.d("Usuario", "Error Respuesta en Json: " + error.message)
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
    private fun getAllUsuarios(view: View) {
        val url =
            "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios/"
        //Gestionar peticion del archivo JSON
        //Crear nueva cola de peticiones
        val requestQueue = Volley.newRequestQueue(context)

        //NUeva peticion JsonObject
        val jsArrayRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                autoCompleteAdapter = ArrayAdapter<String>(view.context,
                    android.R.layout.simple_expandable_list_item_1, parseListUsuraiosJson(response))

                autoCompleteTextView = view.findViewById(R.id.add_votante_autoCompleteText)

                autoCompleteTextView?.threshold = 1

                autoCompleteTextView?.setAdapter(autoCompleteAdapter)
                autoCompleteAdapter?.notifyDataSetChanged()
            })
        {
                error -> Log.d("AddVotanteByUser", "Error Respuesta en Json: " + error.message)
        }

        // Añadir peticion a la cola
        requestQueue.add(jsArrayRequest)
    }

    private fun parseListUsuraiosJson(jsonObject: JSONObject): List<String> {
        //Variables Locales
        val usuarios: MutableList<String> = ArrayList<String>()
        var jsonArray: JSONArray? = null
        try {
            //Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                try {
                    val objeto = jsonArray.getJSONObject(i)
                    val usuario = objeto.getString("username")
                    usuarios.add(usuario)
                    Log.i("AddVotanteByUser", "Usuario añadido")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return usuarios
    }

    /*private fun searchUsuarios(search: String) {
        val url =
            "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/usuarios/search/"
        //Gestionar peticion del archivo JSON
        //Crear nueva cola de peticiones
        val requestQueue = Volley.newRequestQueue(context)

        //NUeva peticion JsonObject
        val jsArrayRequest = JsonObjectRequest(Request.Method.GET, "$url/$search", null,
            { response ->
                searchUsuariosList = parseListUsuraiosJson(response)
                autoCompleteAdapter?.notifyDataSetChanged()
//                Log.i(SalaAdapter.TAG,"Se recupero el json: $response")
//                Log.i(SalaAdapter.TAG,"sList: ${sList.toString()}")
                //notifyDataSetChanged()
            })
        {
                error -> Log.d("AddVotanteByUser", "Error Respuesta en Json: " + error.message)
        }

        // Añadir peticion a la cola
        requestQueue.add(jsArrayRequest)
    }*/
}