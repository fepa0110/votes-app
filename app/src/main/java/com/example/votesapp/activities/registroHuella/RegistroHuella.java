package com.example.votesapp.activities.registroHuella;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.Script;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.votesapp.R;
import com.example.votesapp.activities.newLogin.NewLogin;
import com.example.votesapp.activities.opciones_votacion.CargaDatosOP;
import com.example.votesapp.activities.registro_usuario.Registro_usuario2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RegistroHuella extends AppCompatActivity {

    private Typeface fuenteTitulo;
    private TextView tituloHuella;
    private TextView tituloDactilar;
    private TextView textInformacion;
    private TextView textInformacion2;
    private Button btnSiguiente;
    private Button btnCancelar;
    private String userName, correo, contrasenia;

    //Conexion
    private String url = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/seguridad/";
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjReq;
    private String TAG = "Error envio Seguridad";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_huella);

        tituloHuella = (TextView) findViewById(R.id.textHuella);
        tituloDactilar = (TextView) findViewById(R.id.textDactilar);
        textInformacion = (TextView) findViewById(R.id.textInformacion);
        textInformacion2 = (TextView) findViewById(R.id.textInformacion2);
        btnCancelar = (Button) findViewById(R.id.buttonCancelar);
        btnSiguiente = (Button) findViewById(R.id.buttonSiguiente);

        userName = getIntent().getStringExtra("param_username");

        String urlFuente = "fuentes/GothamBold.ttf";

        fuenteTitulo = Typeface.createFromAsset(getAssets(),urlFuente);
        tituloHuella.setTypeface(fuenteTitulo);
        tituloDactilar.setTypeface(fuenteTitulo);
        textInformacion.setText("Se almacenara la siguiente informacion de su dispositivo con el fin de utilizarlo como contenedor " +
                "para sus huellas digitales. ");
        textInformacion2.setText(Html.fromHtml("<ul><li><b>&nbsp;Modelo</b></li><li><b>&nbsp;ID del Dispositivo</b></li></ul>"));

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarInformacion();
                finish();
            }
        });


    }

    private void guardarInformacion(){

        requestQueue= Volley.newRequestQueue(this);

        JSONObject params = new JSONObject();
        try {
            params.put("idSmartPhone", Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
            params.put("modeloSmartphone", Build.MODEL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObjReq = new JsonObjectRequest(Request.Method.POST, url + userName, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Guardado Correctamente", response.toString() + "//// " +userName);
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