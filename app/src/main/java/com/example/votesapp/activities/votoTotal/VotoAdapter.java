package com.example.votesapp.activities.votoTotal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class VotoAdapter extends ArrayAdapter<String> {

    //Atributos
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;
    private String urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas/";
    List<Voto> lVotos;

    public VotoAdapter(Context context,int idSala) {
        super(context,0);

        //Crear nueva cola de peticiones
        requestQueue= Volley.newRequestQueue(context);

        //nueva peticion JSONObject
        jsArrayRequest = new JsonObjectRequest(Request.Method.GET, urlBase+idSala, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                lVotos = parseJson(response);
                notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //Añadir peticion a la cola
        requestQueue.add(jsArrayRequest);
    }



    //gestionar peticion del archivo
    @Override
    public int getCount() {
        return lVotos != null ? lVotos.size() : 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //Salvando la referencia del View de la fila
//        View listaVotosView = convertView;
        View listaVotosView;

        //Comprobando si el view no existe
//        if (null == convertView){
//            //Si no existe, entonces inflarlo
//            listaVotosView = layoutInflater.inflate(R.layout.item_voto,parent,false);
//        }
        listaVotosView = null == convertView ? layoutInflater.inflate(R.layout.item_voto,parent,false): convertView;
        //Obtener el item actual
        Voto voto = lVotos.get(position);

        //Obtener View
        TextView numeroSala = (TextView) listaVotosView.findViewById(R.id.numeroSala);
        TextView nombreSala = (TextView)listaVotosView.findViewById(R.id.nombreSala);
        TextView cantidadVoto = (TextView) listaVotosView.findViewById(R.id.cantidadVoto);

        //Actualizar los View
        nombreSala.setText(voto.getNombreSala());
        cantidadVoto.setText(voto.getCantidadVoto()+"");

        return listaVotosView;
    }

    public List<Voto> parseJson(JSONObject jsonObject){
        // Variables locales
        List<Voto> votos = new ArrayList();
        JSONArray jsonArray= null;

        try {
            // Obtener el array del objeto
            JSONObject data = jsonObject.getJSONObject("data");
            Log.i("votoAdapter","Response: " + data.toString());
            jsonArray = data.getJSONArray("opVotacion");

            for(int i=0; i<jsonArray.length(); i++){

                try {
                    JSONObject objeto= jsonArray.getJSONObject(i);


                    Voto infovoto = new Voto(objeto.getInt("id"),objeto.getString("titulo"),objeto.getInt("cantVotos"));




                    votos.add(infovoto);

                } catch (JSONException e) {
//                    Log.e(TAG, "Error de parsing: "+ e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return votos;
    }
}
