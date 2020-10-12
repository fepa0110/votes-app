package com.example.votesapp.activities.mis_salas

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.R
//import com.example.votesapp.activities.lista_salas.Sala
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class SalaAdapter(context: Context?, urlComplement: String) : ArrayAdapter<Sala?>(
    context!!, 0
) 
{

    companion object {
        private const val TAG = "SalaAdapter"
    }

    private var urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas/"

    private val requestQueue: RequestQueue
    var jsArrayRequest: JsonObjectRequest
    var sList: List<Sala?>? = null
    
    //Constructor
    init {
        //Gestionar peticion del archivo JSON
        this.urlBase = urlBase + urlComplement
        //Crear nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(context)

        //NUeva peticion JsonObject
        jsArrayRequest = JsonObjectRequest(Request.Method.GET, urlBase, null, 
            { response ->
                sList = parseJson(response)
                Log.i(TAG,"Se recupero el json: $response")
                Log.i(TAG,"sList: ${sList.toString()}")
                notifyDataSetChanged()
            }) 
            { 
                error -> Log.d(TAG, "Error Respuesta en Json: " + error.message) 
            }

        // Añadir peticion a la cola
        requestQueue.add(jsArrayRequest)
    }

    /* public fun setComplementUrl(complementUrl: String){
        this.urlBase = urlBase + complementUrl
    }*/

    override fun getCount(): Int {
        return if (sList != null) sList!!.size else 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(parent.context)

        //Salvando la referencia del View de la fila
        var view = convertView

        //comprobando si el View no existe
        if (null == view) {
            //Si no existe, entonces inflarlo
            view = layoutInflater.inflate(R.layout.items_mis_salas, parent, false)
        }

        //Procesar item
        //obtener el data actual
        val sala = sList!![position]

        //obtener View
        //Definir lo que acabamos de crear en el item_sala para q la encuentre el listView y
        //los pueble con los datos
        val idSala = view!!.findViewById<TextView>(R.id.item_id_mis_salas)
        idSala.text = sala!!.id

        val nombreSala = view.findViewById<TextView>(R.id.item_nombre_mis_salas)
        nombreSala.text = sala.nombreSala

        return view
    }

    //parceo el Json
    private fun parseJson(jsonObject: JSONObject): List<Sala?> {
        //Variables Locales
        val salas: MutableList<Sala?> = ArrayList<Sala?>()
        var jsonArray: JSONArray? = null
        try {
            //Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                try {
                    val objeto = jsonArray.getJSONObject(i)
                    val sala = Sala(objeto.getString("id"), objeto.getString("nombre"))
                    salas.add(sala)
                    Log.i(TAG, "Sala añadida")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return salas
    }
}