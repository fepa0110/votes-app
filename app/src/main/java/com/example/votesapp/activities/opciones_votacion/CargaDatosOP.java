package com.example.votesapp.activities.opciones_votacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.votesapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CargaDatosOP extends AppCompatActivity {

    private TextInputEditText titulo, descripcion;
    private Button btnCancelar, btnGuardar, btnEliminar;
    private JsonObjectRequest jsonObjReq;
    private RequestQueue requestQueue;
    private String url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/opVotaciones/";
    private String TAG = "OPVotacion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_datos_op);

        int salaIdPrueba = getIntent().getIntExtra("param_sala_id",0);
        Toast.makeText(CargaDatosOP.this, "Carga datos: id -> "+salaIdPrueba, Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, ""+getIntent().getBooleanExtra("param_editable", false), Toast.LENGTH_SHORT).show();

        requestQueue= Volley.newRequestQueue(this);

        titulo = findViewById(R.id.Text_Titulo_opVotacion);
        titulo.setText(getIntent().getStringExtra("param_titulo"));

        descripcion = findViewById(R.id.Text_Descripcion_opVotacion);
        descripcion.setText(getIntent().getStringExtra("param_descripcion"));

        //Boton Cancelar
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Boton btnGuardar
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Boton", "Entro al boton de guardar");

                if (getIntent().getBooleanExtra("param_editable", false) == true) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("id", getIntent().getIntExtra("param_id",0));
                        params.put("titulo", titulo.getText().toString());
                        params.put("descripcion", descripcion.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jsonObjReq = new JsonObjectRequest(Request.Method.PUT, url, params,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(CargaDatosOP.this, "Opcion Modificada Correctamente", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            Log.i(TAG, error.getMessage());
                        }
                    });
                    requestQueue.add(jsonObjReq);

                } else {

                    String auxUrl = url;
                    int salaIdInt = getIntent().getIntExtra("param_sala_id",0);
                    auxUrl += String.valueOf(salaIdInt);

                    JSONObject params = new JSONObject();
                    try {
                        params.put("titulo", titulo.getText().toString());
                        params.put("descripcion", descripcion.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jsonObjReq = new JsonObjectRequest(Request.Method.POST, auxUrl, params,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(CargaDatosOP.this, "Opcion Agregada Correctamente", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            Log.i(TAG, error.getMessage());
                        }
                    });
                    requestQueue.add(jsonObjReq);
                    descripcion.setText("");
                    titulo.setText("");

                }
            }
        });


        btnEliminar = (Button) findViewById(R.id.btnEleminar);
        if (getIntent().getBooleanExtra("param_editable", false) != true){
            btnEliminar.setVisibility(View.INVISIBLE);
        }

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String auxUrl = url;
                Log.i("Boton", "Entro al boton de eliminar");
                auxUrl += getIntent().getIntExtra("param_sala_id",0) + "/" +
                        getIntent().getIntExtra("param_id",0);
                Log.i("Boton", "URL:" + url);
                jsonObjReq = new JsonObjectRequest(Request.Method.DELETE, auxUrl, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(CargaDatosOP.this, "Opcion Eliminada Correctamente", Toast.LENGTH_SHORT).show();
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
        });

    }
}