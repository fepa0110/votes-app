package com.example.votesapp.activities.lista_salas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.votesapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SalaAdapter extends ArrayAdapter<Sala> {

    //Atributos
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;
    private String URL_BASE = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas";
    private static final String TAG = "SalaAdapter";
    List<Sala> sList ;

    //Constructor

    public SalaAdapter(Context context){
        super(context,0);




        //Gestionar peticion del archivo JSON

        //Crear nueva cola de peticiones
        requestQueue= Volley.newRequestQueue(context);

       //NUeva peticion JsonObject
        jsArrayRequest = new JsonObjectRequest(Request.Method.GET, URL_BASE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                sList = parseJson(response);
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Error Respuesta en Json: " + error.getMessage());

            }
        });
//        //  Añadir peticion a la cola
        requestQueue.add(jsArrayRequest);
//
    }

    @Override
    public int getCount() {
        return sList != null ? sList.size() : 0 ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

       //Salvando la referencia del View de la fila
            View view = convertView;

        //comprobando si el View no existe
        if (null == view) {
            //Si no existe, entonces inflarlo
            view = layoutInflater.inflate(R.layout.item_sala, parent, false);
        }


        //Procesar item
        //obtener el data actual
        Sala sala = sList.get(position);

        //obtener View
        //Definir lo que acabamos de crear en el item_sala para q la encuentre el listView y
        //los pueble con los datos
        TextView idSala = view.findViewById(R.id.idSala);
        idSala.setText(sala.getId());
        TextView nombreSala = view.findViewById(R.id.nombreSala);
        nombreSala.setText(sala.getNombreSala());

        return view;
    }
    //parceo el Json
    public List<Sala> parseJson (JSONObject jsonObject){
        //Variables Locales
        List<Sala> salas = new ArrayList();
        JSONArray jsonArray = null;

        try {
            //Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("data");

            for (int i=0; i<jsonArray.length();i++){
                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);
                    Sala sala = new Sala(objeto.getString("id"),objeto.getString("nombre"));


                    salas.add(sala);

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }


        }catch (JSONException e){
            e.printStackTrace();
        }
        return salas;
    }
}
