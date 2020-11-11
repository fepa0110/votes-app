package com.example.votesapp.activities.user_activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.votesapp.activities.opciones_votacion.CargaDatosOP;
import com.example.votesapp.model.Seguridad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DispositivosAdapter extends ArrayAdapter {

    //Atributos
    private List<Seguridad> dispositivosList;
    private String username;
    private LayoutInflater layoutInflater;
    private String url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/seguridad";
    private RequestQueue requestQueue;
    private JsonArrayRequest jsonArrayRequest;
    private String TAG = "Dispositivo";
    private Button btnRemover;


    //Constructor
    public DispositivosAdapter(@NonNull Context context, String username) {
        super(context, 0);
        dispositivosList = new ArrayList<>();
        this.username = username;
        getModelos();
    }


    @Override
    public int getCount() {
        if (dispositivosList != null) {
            return dispositivosList.size();
        }
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        layoutInflater = LayoutInflater.from(parent.getContext());

        View view = convertView;

        if (null == view) {
            view = layoutInflater.inflate(R.layout.item_dispositivo, parent, false);
        }

        //Se obtiene el valor actual
        String modelo = dispositivosList.get(position).getModeloSmartphone();

        TextView itemModelo = view.findViewById(R.id.item_modelo_dispositivo);
        itemModelo.setText(modelo);

        btnRemover = view.findViewById(R.id.item_remove_dispositivo);
        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarDispositivo(view, position);
                dispositivosList.remove(position);
                notifyDataSetChanged();

            }
        });

        return view;
    }


    public void getModelos(){
        requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url+"/"+username,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dispositivosList = parseJson(response);
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

    private List<Seguridad> parseJson(JSONObject jsonObject){
        // Variables locales
        List<Seguridad> posts = new ArrayList();
        JSONArray jsonArray = null;

        try {

            jsonArray = jsonObject.getJSONArray("data");

            for(int i=0; i<jsonArray.length(); i++){

                try {
                    JSONObject objeto= jsonArray.getJSONObject(i);
                    Seguridad segu = new Seguridad();
                    segu.setId(objeto.getInt("id"));
                    segu.setModeloSmartphone(objeto.getString("modeloSmartphone"));
                    posts.add(segu);
                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: "+ e.getMessage());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public void eliminarDispositivo(View view, int position){
        requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE, url +"/"+ dispositivosList.get(position).getId(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(view.getContext(), "Dispositivo Eliminado Correctamente", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.i(TAG, error.getMessage());
            }
        });
        requestQueue.add(jsonObjReq);
    }
}
