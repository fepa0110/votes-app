package com.example.votesapp.activities.opciones_votacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.an.biometric.BiometricCallback;
import com.an.biometric.BiometricManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.votesapp.R;
import com.example.votesapp.model.Seguridad;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CargaDatosOP extends AppCompatActivity implements BiometricCallback {

    private TextInputEditText titulo, descripcion;
    private TextInputLayout tituloLayout, descripcionLayout;
    private Button btnCancelar, btnGuardar, btnEliminar, btnVotar;
    private JsonObjectRequest jsonObjReq;
    private RequestQueue requestQueue;
    private String url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/opVotaciones/";
    private String urlVotacion = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas/";
    private String urlDispositivo = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/seguridad/";
    private String urlVoto="http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas/userVoto/";
    private String TAG = "OPVotacion";
    private int cantVotos;
    private Handler handler;
    private boolean desdeSalas;
    private boolean userVoto = false;
    private int salaId;
    private String userName;
    private String estado;
    BiometricManager mBiometricManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_datos_op);

        requestQueue= Volley.newRequestQueue(this);

        //Recibo los componentes de la actividad
        titulo = findViewById(R.id.Text_Titulo_opVotacion);
        tituloLayout = findViewById(R.id.titulo_opVotacion);
        descripcion = findViewById(R.id.Text_Descripcion_opVotacion);
        descripcionLayout = findViewById(R.id.descripcion_opVotacion);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnEliminar = (Button) findViewById(R.id.btnEleminar);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnVotar = (Button) findViewById(R.id.btnVotar);

        titulo.setText(getIntent().getStringExtra("param_titulo"));
        descripcion.setText(getIntent().getStringExtra("param_descripcion"));
        cantVotos = getIntent().getIntExtra("param_cantVotos",0);
        this.salaId = getIntent().getIntExtra("param_sala_id",0);
        this.userName = getIntent().getStringExtra("param_username");
        this.estado = getIntent().getStringExtra("param_estado");
        this.desdeSalas = getIntent().getBooleanExtra("param_desdeSalas",false);

        if(userName != null) {
            verificarVoto();
        }

        if (estado != null) {
            if (estado.equals("FINALIZADA") || estado.equals("DISPONIBLE")) {
                btnCancelar.setVisibility(View.INVISIBLE);
                btnEliminar.setVisibility(View.INVISIBLE);
                btnGuardar.setVisibility(View.INVISIBLE);
                btnVotar.setVisibility(View.INVISIBLE);
                titulo.setEnabled(false);
                descripcion.setEnabled(false);
            }
        }
        
        if(desdeSalas){
            btnCancelar.setVisibility(View.INVISIBLE);
            btnEliminar.setVisibility(View.INVISIBLE);
            btnGuardar.setVisibility(View.INVISIBLE);
            btnVotar.setVisibility(View.VISIBLE);
            titulo.setEnabled(false);
            descripcion.setEnabled(false);
        }



        btnVotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarDispositivo();

            }
        });

        titulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titulo.setError(null);
            }
        });

        //Boton Cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (titulo.getText().toString().isEmpty()) {
                    tituloLayout.setError("El campo no puede estar vacio");
                } else {
                    if (getIntent().getBooleanExtra("param_editable", false) == true) {
                        JSONObject params = new JSONObject();
                        try {
                            params.put("id", getIntent().getIntExtra("param_id", 0));
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
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onBackPressed();
                            }
                        },500);

                    } else {

                        String auxUrl = url;
                        int salaIdInt = getIntent().getIntExtra("param_sala_id", 0);
                        auxUrl += String.valueOf(salaIdInt);

                        JSONObject params = new JSONObject();
                        try {
                            params.put("titulo", titulo.getText().toString());
                            params.put("descripcion", descripcion.getText().toString());
                            params.put("cantVotos",0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonObjReq = new JsonObjectRequest(Request.Method.POST, auxUrl, params,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(CargaDatosOP.this, "Opcion Agregada Correctamente", Toast.LENGTH_SHORT).show();
                                        finish();
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
            }
        });

        //Boton eliminar
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
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                },500);
            }
        });

    }

    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
//        Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show();
        mBiometricManager.cancelAuthentication();
    }

    @Override
    public void onAuthenticationSuccessful() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_success), Toast.LENGTH_LONG).show();
        JSONObject params = new JSONObject();
        try {
            params.put("id", getIntent().getIntExtra("param_id", 0));
            params.put("titulo", titulo.getText().toString());
            params.put("descripcion", descripcion.getText().toString());
            params.put("cantVotos",cantVotos+1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObjReq = new JsonObjectRequest(Request.Method.PUT, urlVotacion+"addVotacion/"+salaId+"/"+userName, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(CargaDatosOP.this, "Opcion Modificada Correctamente", Toast.LENGTH_SHORT).show();
                        onBackPressed();
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

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
//        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
//        Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }

    private void verificarDispositivo(){
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlDispositivo+userName,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Seguridad> modelos = parseJson(response);
                        for (int i=0; i<modelos.size();i++) {
                            if (userVoto != true) {
                                if (modelos.get(i).getIdSmartPhone().equals(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))) {
                                    mBiometricManager = new BiometricManager.BiometricBuilder(CargaDatosOP.this)
                                            .setTitle(getString(R.string.biometric_title))
                                            .setSubtitle(getString(R.string.biometric_subtitle))
                                            .setDescription(getString(R.string.biometric_description))
                                            .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                                            .build();

                                    //start authentication
                                    mBiometricManager.authenticate(CargaDatosOP.this);
                                } else {
                                    Toast.makeText(CargaDatosOP.this, "No puede votar desde este dispositivo", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(CargaDatosOP.this, "Usted ya voto", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }
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
                    segu.setIdSmartPhone(objeto.getString("idSmartPhone"));
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

    private void verificarVoto(){
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlVotacion+salaId+"/"+userName,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJson2(response);
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

    private void parseJson2(JSONObject jsonObject){
        // Variables locales
        try {

            JSONObject json = jsonObject.getJSONObject("data");

                try {
                    userVoto = json.getBoolean("voto");
                    Log.i("Votooooooooo", userVoto+"");
                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: "+ e.getMessage());
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}