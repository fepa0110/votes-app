package com.example.votesapp.activities.opciones_votacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.votesapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CargaDatosOP extends AppCompatActivity {

    private TextInputEditText titulo, descripcion;
    private Button btnCancelar, btnGuardar;
    private JsonObjectRequest jsonObjReq;
    private String url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/opVotacion";
    private String TAG = "OPVotacion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_datos_op);

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

                    jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d(TAG, response.toString());

                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());

                        }
                    }
                    ) {

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("titulo", titulo.getText().toString());
                            params.put("descripcion", descripcion.getText().toString());
                            return params;
                        }
                    };
                            }
        });

    }
}