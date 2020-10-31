package com.example.votesapp.activities.votante_by_dni;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.votesapp.R;
import com.example.votesapp.activities.opciones_votacion.Adapter;
import com.example.votesapp.activities.opciones_votacion.CargaDatosOP;
import com.example.votesapp.activities.opciones_votacion.Modelo_OpVt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccesoDniAdapter extends ArrayAdapter {

    //Atributos
    private List<String> dniList;
    private int salaId;
    private LayoutInflater layoutInflater;
    private String url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas/addByDni";
    private RequestQueue requestQueue;
    private JsonArrayRequest jsonArrayRequest;
    private String TAG = "Acceso DNI";


    //Constructor
    public AccesoDniAdapter(@NonNull Context context, int salaId) {
        super(context, 0);
        dniList = new ArrayList<>();
        this.salaId = salaId;
        getVotantes();
    }

    /**
     * Metodo que agrega a la lista un dni
     *
     * @param dni Parametro que se obtiene del TextInput de la actividad
     */
    public void addDni(String dni) {
        dniList.add(dni);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (dniList != null) {
            return dniList.size();
        }
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        layoutInflater = LayoutInflater.from(parent.getContext());

        View view = convertView;

        if (null == view) {
            view = layoutInflater.inflate(R.layout.item_votante_dni, parent, false);
        }

        //Se obtiene el valor actual
        String dni = dniList.get(position);

        TextView itemDni = view.findViewById(R.id.item_id_dni);
        itemDni.setText(dni);

        return view;
    }

    public boolean buscarDni(String dni) {
        for (int i = 0; i < dniList.size(); i++) {
            if (dni.equals(dniList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void sendVotantesByDni() {
        requestQueue = Volley.newRequestQueue(getContext());

        JSONArray jsonArray = new JSONArray();
        JSONObject param;

        for (int i = 0; i < dniList.size(); i++) {
            param = new JSONObject();
            try {
                param.put("dni", dniList.get(i));
                jsonArray.put(param);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url + "/" + salaId, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getContext(), "Datos cargados correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.i(TAG, error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void getVotantes(){
        requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url+"/"+salaId,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dniList = parseJson(response);
                        notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());
                    }
                }
        );
        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);
    }

    private List<String> parseJson(JSONObject jsonObject){
        // Variables locales
        List<String> posts = new ArrayList();
        JSONArray jsonArray = null;

        try {

            jsonArray = jsonObject.getJSONArray("data");

            for(int i=0; i<jsonArray.length(); i++){

                try {
                    JSONObject objeto= jsonArray.getJSONObject(i);
                    posts.add(objeto.getString("dni"));
                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: "+ e.getMessage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }
}
