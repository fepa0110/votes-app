package com.example.votesapp.activities.votante_by_user

//import com.example.votesapp.activities.lista_salas.Sala
import android.app.VoiceInteractor
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.R
import com.example.votesapp.model.Usuario
import com.example.votesapp.services.SalaService
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class VotanteByUsernameAdapter(context: Context?, idSala: Int) : ArrayAdapter<Usuario?>(
    context!!, 0
) {

    companion object {
        private const val TAG = "VotanteByUsernameAdapter"
    }

    var usuarioList: List<Usuario?>? = null
    var usuarios: MutableList<Usuario?> = ArrayList<Usuario?>()
    val idSala : Int = idSala

    //Constructor
    init {
        usuarioList = usuarios
    }

    fun addUser(usuario: Usuario) {
        usuarios.add(usuario)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return if (usuarioList != null) usuarioList!!.size else 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(parent.context)

        //Salvando la referencia del View de la fila
        var view = convertView

        //comprobando si el View no existe
        if (null == view) {
            //Si no existe, entonces inflarlo
            view = layoutInflater.inflate(R.layout.items_votante_username, parent, false)
        }

        //Procesar item
        //obtener el data actual
        val usuario = usuarioList!![position]

        //obtener View
        //Definir lo que acabamos de crear en el item_sala para q la encuentre el listView y
        //los pueble con los datos
        val idSala = view!!.findViewById<TextView>(R.id.item_id_votante_username)
        //idSala.text = sala!!.id

        val usernameItem = view.findViewById<TextView>(R.id.item_user_votante_username)
        usernameItem.text = usuario?.username

        val nombreCompletoItem = view.findViewById<TextView>(R.id.item_user_votante_nombreCompleto)
        val nombreCompleto = usuario?.nombre + " " + usuario?.apellido
        nombreCompletoItem.text = nombreCompleto

        val removeVotanteButton = view.findViewById<Button>(R.id.item_remove_votante_username)
        removeVotanteButton.setOnClickListener {
            usuarios.removeAt(position)
            notifyDataSetChanged()
        }

        return view
    }

    fun containUser(usuario: Usuario) : Boolean {
        return usuarios.contains(usuario)
    }

    fun sendVotantes() {
        val url =
            "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas/addByUsername"

        val requestQueue= Volley.newRequestQueue(context)
        //Gestionar peticion del archivo JSON

        //Crear nueva cola de peticiones

        val jsonUsuarios = JSONObject()
        
        val usuariosArray = JSONArray()

        for(usuario in usuarios){
            val username = usuario?.username
            val jsonBody = JSONObject()
            jsonBody.put("username",username)
            usuariosArray.put(jsonBody)
        }

        Log.i(TAG, usuariosArray.toString(0))

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.POST,"$url/$idSala", usuariosArray,
            { response ->
                Log.i(TAG, "Response is: $response")
                Toast.makeText(context, "Usuarios agregados correctamente", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(context, "Usuarios agregados correctamente", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
//                Log.e(SalaService.LOG_TAG, "No se pudo crear la sala")
//                Toast.makeText(context, "No se pudo crear la sala", Toast.LENGTH_SHORT).show()
            }
        )

        // Añadir peticion a la cola
        requestQueue.add(jsonArrayRequest)
    }

}