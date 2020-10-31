package com.example.votesapp.services;


import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.votesapp.R;
import com.example.votesapp.activities.opciones_votacion.Adapter;
import com.example.votesapp.activities.opciones_votacion.Modelo_OpVt;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OPVotacionService extends ArrayAdapter {

    private String url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas/";
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;
    List<Modelo_OpVt> items;
    private static final String TAG = "PostAdapter";
    ViewPager viewPager;
    Adapter adapter;

    public OPVotacionService(Context context, ViewPager viewPager1, int salaId, String userName, boolean desdeSalas) {
        super(context, 0);

        this.url += salaId;

        items = new ArrayList<>();
        viewPager = viewPager1;

        //Crear nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(context);
        // Nueva petición JSONObject
        jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        items = parseJson(response);
                        //Obtener instancia de la actividad
                        adapter = new Adapter(items,context, salaId, userName, desdeSalas);
                        viewPager.setAdapter(adapter);
                        viewPager.setPadding(130, 0, 130, 0);
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

    public List<Modelo_OpVt> parseJson(JSONObject jsonObject){
        // Variables locales
        List<Modelo_OpVt> posts = new ArrayList();
        JSONArray jsonArray = null;

        try {
            JSONObject data = jsonObject.getJSONObject("data");

            Log.i(TAG,"Response: " + data.toString());

            jsonArray = data.getJSONArray("opVotacion");

            Log.i(TAG,"Response 2: " + jsonArray.toString());

            for(int i=0; i<jsonArray.length(); i++){

                try {
                    JSONObject objeto= jsonArray.getJSONObject(i);

                    Modelo_OpVt post = new Modelo_OpVt(
                            objeto.getInt("id"),
                            objeto.getString("titulo"),
                            objeto.getString("descripcion"),
                            objeto.getInt("cantVotos"));


                    posts.add(post);

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