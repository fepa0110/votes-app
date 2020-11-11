package com.example.votesapp.activities.lista_salas

//import androidx.core.content.ContextCompat.startActivity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.votesapp.R
import com.example.votesapp.activities.menuSala.MenuSala
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class SalaAdapter(context: Context?, username: String) : ArrayAdapter<Sala?>(
    context!!, 0
) {
    //Atributos
    private var requestQueue: RequestQueue
    var jsArrayRequest: JsonObjectRequest
    var username = username
    var getDate = GregorianCalendar.getInstance()
    var fechaSala : List<String> = ArrayList()
    var horarioSala : List<String> = ArrayList()
    var tiempoSalaFinalizada : Boolean = false
    private var jsonObjReq: JsonObjectRequest? = null



    private var urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas"
    private var urlDuracionSala = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/duraciones"

    var sList: List<Sala?>? = ArrayList<Sala?>()

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
            view = layoutInflater.inflate(R.layout.item_sala, parent, false)
        }

        //Procesar item
        //obtener el data actual
        val sala = sList!![position]

        //obtener View
        //Definir lo que acabamos de crear en el item_sala para q la encuentre el listView y
        //los pueble con los datos
        val idSala = view!!.findViewById<TextView>(R.id.idSala)
        //idSala.text = sala!!.id
        val nombreSala = view.findViewById<TextView>(R.id.nombreSala)
        nombreSala.text = sala?.nombreSala
        view.setOnClickListener{
            val horasActual = getDate[Calendar.HOUR].toString() + ":" + getDate[Calendar.MINUTE] + " " +getDate[Calendar.AM_PM]
            val fechaActual = getDate[Calendar.YEAR].toString() + "-" + (getDate[Calendar.MONTH] + 1) + "-" + getDate[Calendar.DAY_OF_MONTH]

            if(sala?.contrasenia.toString().equals("null")){
                requestQueue = Volley.newRequestQueue(context)
                jsArrayRequest = JsonObjectRequest(
                    Request.Method.GET, "$urlDuracionSala/${sala?.id}", null,
                    { response ->
                        val fecha = parseJsonDuracion(response)
                        var hora = getDate[Calendar.HOUR]

                        if (fechaSala[0]?.toInt() > getDate[Calendar.YEAR]) {
                            Log.i("Año mayor", "entra")
                            tiempoSalaFinalizada = true
                        } else {
                            if (fechaSala[0]?.toInt() == getDate[Calendar.YEAR]) {
                                if (fechaSala[1]?.toInt() > (getDate[Calendar.MONTH] + 1)) {
                                    Log.i("mes mayor", "entrar")
                                    tiempoSalaFinalizada = true
                                } else {
                                    if (fechaSala[1]?.toInt() == (getDate[Calendar.MONTH] + 1)) {
                                        if (fechaSala[2]?.toInt() > getDate[Calendar.DAY_OF_MONTH]) {
                                            Log.i("dia mayor", "entrar")
                                            tiempoSalaFinalizada = true
                                        } else {
                                            if (fechaSala[2]?.toInt() == getDate[Calendar.DAY_OF_MONTH]) {
                                                if (getDate[Calendar.AM_PM] == Calendar.PM) {
                                                    hora = hora + 12
                                                }
                                                if (horarioSala[0]?.toInt() > hora) {
                                                    Log.i("Hora mayor", "entrar")
                                                    tiempoSalaFinalizada = true
                                                } else {
                                                    if (horarioSala[0]?.toInt() == hora) {
                                                        if (horarioSala[1]?.toInt() > getDate[Calendar.MINUTE]) {
                                                            Log.i("Minutos mayor", "Entrar")
                                                            tiempoSalaFinalizada = true
                                                        } else {
                                                            Log.i("minutos menor", "no entrar")
                                                            tiempoSalaFinalizada = false
                                                        }
                                                    } else {
                                                        Log.i("hora menor", "no entrar")
                                                        tiempoSalaFinalizada = false
                                                    }
                                                }
                                            } else {
                                                Log.i("dia menor", "no entrar")
                                                tiempoSalaFinalizada = false
                                            }
                                        }
                                    } else {
                                        Log.i("mes menor", "no entrar")
                                        tiempoSalaFinalizada = false
                                    }
                                }
                            } else {
                                Log.i("año menor", "no entrar")
                                tiempoSalaFinalizada = false
                            }
                        }
                        if (tiempoSalaFinalizada == false) {
                            requestQueue = Volley.newRequestQueue(context)
                            val urlBase =
                                "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas"
                            val params = JSONObject()
                            try {
                                params.put("estado", "FINALIZADA")
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                            jsonObjReq = JsonObjectRequest(
                                Request.Method.PUT,
                                "$urlBase/estadoSala/${sala?.id}", params,

                                {
//                                    val mensaje = Snackbar.make(
//                                        view,
//                                        "Sala Habilitada correctamente",
//                                        Snackbar.LENGTH_LONG
//                                    )
//                                    mensaje.show()
                                    val intent = Intent(context, MenuSala::class.java)

                                    intent.putExtra("param_username", username)
                                    intent.putExtra("param_estado", sala?.estado)
                                    intent.putExtra("param_id", sala?.id?.toInt())
                                    intent.putExtra("param_nombre", sala?.nombreSala)
                                    intent.putExtra("param_desde_salas", true)
                                    intent.putExtra("param_tiempoSala", tiempoSalaFinalizada)

                                    context.startActivity(intent)

                                }) { error ->
                                VolleyLog.d(TAG, "Error: " + error.message)
                                Log.i(TAG, error.message)
                            }
                            requestQueue.add<JSONObject>(jsonObjReq)
                        }else{
                            val intent = Intent(context, MenuSala::class.java)

                            intent.putExtra("param_username", username)
                            intent.putExtra("param_estado", sala?.estado)
                            intent.putExtra("param_id", sala?.id?.toInt())
                            intent.putExtra("param_nombre", sala?.nombreSala)
                            intent.putExtra("param_desde_salas", true)
                            intent.putExtra("param_tiempoSala", tiempoSalaFinalizada)

                            context.startActivity(intent)

                        }


                    }) { error -> Log.d(TAG, "Error Respuesta en Json: " + error.message) }
                requestQueue.add(jsArrayRequest)

            }else {
                val idSala = sala?.id?.toInt()
                if (idSala != null) {
                    DialogoContra(
                        context,
                        sala?.contrasenia,
                        idSala.toInt(),
                        sala?.nombreSala,
                        sala?.estado,
                        username
                    )
                };
            }
        }

        return view
    }

    //parceo el Json
    fun parseJson(jsonObject: JSONObject): List<Sala?> {
        //Variables Locales
        val salas: MutableList<Sala?> = ArrayList<Sala?>()
        var jsonArray: JSONArray? = null
        try {
            //Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                try {
                    val objeto = jsonArray.getJSONObject(i)
                    val sala = Sala(
                        objeto.getString("id"), objeto.getString("nombre"), objeto.getString(
                            "contrasenia"
                        )
                    )
                    sala.estado = objeto.getString("estado")
                    if(objeto.getString("estado")!="PENDIENTE") {
                        salas.add(sala)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return salas
    }
    fun parseJsonDuracion(jsonObject: JSONObject) : String? {
        var data : JSONObject

        try {
            data = jsonObject.getJSONObject("data")
            var auxFecha = data.getString("fecha")
            var auxHoras = data.getString("hora")

            fechaSala = auxFecha.split('-')
            horarioSala = auxHoras.split(':')

        }catch (e: JSONException) {
            e.printStackTrace()
        }
        return ""
    }



    companion object {
        private const val TAG = "SalaAdapter"
    }

    //Constructor
    init {

        var salasUser: List<Sala?> = ArrayList<Sala?>()
        var salasDni: List<Sala?> = ArrayList<Sala?>()

        //Gestionar peticion del archivo JSON

        //Crear nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(context)
        //NUeva peticion JsonObject
        jsArrayRequest = JsonObjectRequest(
            Request.Method.GET,
            "$urlBase/userVotante/$username",
            null,
            { response ->
                salasUser = parseJson(response)
                for (i in 0 until salasUser.size) {
                    Log.i("Salas User", salasUser.get(i)?.nombreSala)
                }
                agregarSalas(salasUser)
                //notifyDataSetChanged()
            }) { error -> Log.d(TAG, "Error Respuesta en Json: " + error.message) }
        //        //  Añadir peticion a la cola
        requestQueue.add(jsArrayRequest)
        //

        //NUeva peticion JsonObject
        jsArrayRequest = JsonObjectRequest(
            Request.Method.GET,
            "$urlBase/userVotanteDni/$username",
            null,
            { response ->
                salasDni = parseJson(response)
                for (i in 0 until salasDni.size) {
                    Log.i("Salas DNI", salasDni.get(i)?.nombreSala)
                }
                agregarSalas(salasDni)
                //notifyDataSetChanged()
            }) { error -> Log.d(TAG, "Error Respuesta en Json: " + error.message) }
        //        //  Añadir peticion a la cola
        requestQueue.add(jsArrayRequest)
    }

    fun agregarSalas(salasVotante: List<Sala?>){
        var salas : MutableList<Sala?> = ArrayList<Sala?>()
        for (i in 0 until sList!!.size) {
            salas.add(sList!!.get(i))
        }

        for (i in 0 until salasVotante.size) {
            salas.add(salasVotante.get(i))
        }

        sList = salas
        notifyDataSetChanged()
    }
}