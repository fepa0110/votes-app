package com.example.votesapp.activities.menuMisSalas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.example.votesapp.activities.infoSala.InfoSala;
import com.example.votesapp.activities.mis_salas.Sala;
import com.example.votesapp.activities.opciones_votacion.CargaDatosOP;
import com.example.votesapp.activities.opciones_votacion.OpcionesVotacion;
import com.example.votesapp.activities.votante_by_dni.AccesoDni;
import com.example.votesapp.activities.votante_by_user.AddVotanteByUser;
import com.example.votesapp.activities.votoTotal.Lista_votos;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuMisSalas extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    //variables para cargar el fragment principal
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private  OpcionesVotacion opcionesVotacion;
    private  AddVotanteByUser addVotanteByUser;
    private  InfoSala infoSala;
    private AccesoContrasenia accesoContrasenia;
    private AccesoDni accesoDni;
    private Lista_votos lista_votos;

    private Sala sala;

    private int salaId;
    private String nombreSala = " ";
    private String contrasenia = " ";
    private String estado = " ";
    private String usernameOwner = null;

    private JsonObjectRequest jsonObjReq;
    private String TAG = "estadoHabilitado";

    private ImageButton buttonHabilitar;
    private ImageButton buttonFinalizar;

    private RequestQueue requestQueue;
    private Handler handler;

    TextView tituloMenu;
    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mis_salas);

        toolbar= findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        buttonHabilitar=(ImageButton)findViewById(R.id.botonHabilitar);
        buttonFinalizar = findViewById(R.id.button_finalizar_sala);

        salaId = getIntent().getIntExtra("param_id",0);
        nombreSala= getIntent().getStringExtra("param_nombre");
        usernameOwner = getIntent().getStringExtra("param_username");
        contrasenia = getIntent().getStringExtra("param_contrasenia");
        estado = getIntent().getStringExtra("param_estado");

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);

        //establecer el evento onclick al navigationView
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        tituloMenu=header.findViewById(R.id.tituloMenu);
        tituloMenu.setText(nombreSala);

        this.getSala();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        sala = new Sala();

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        Bundle bundle= new Bundle();
        bundle.putInt("param_id",salaId);
        bundle.putString("param_username",usernameOwner);
        bundle.putString("param_contrasenia",contrasenia);
        bundle.putString("param_estado",estado);

        opcionesVotacion = new OpcionesVotacion();
        opcionesVotacion.setArguments(bundle);

        addVotanteByUser = new AddVotanteByUser();
        addVotanteByUser.setArguments(bundle);

        infoSala = new InfoSala();
        infoSala.setArguments(bundle);

        accesoContrasenia = new AccesoContrasenia();
        accesoContrasenia.setArguments(bundle);

        accesoDni = new AccesoDni();
        accesoDni.setArguments(bundle);

        lista_votos = new Lista_votos();
        lista_votos.setArguments(bundle);

        buttonFinalizar.setOnClickListener(
                view -> {
                    finalizarSala(view);
                    buttonFinalizar.setBackgroundTintList(getResources().getColorStateList(R.color.gris));
                    buttonFinalizar.setEnabled(false);
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    },500);
                    
                }
        );

        buttonHabilitar.setOnClickListener(
            view -> {
                habilitarSala(view);
                buttonHabilitar.setBackgroundTintList(getResources().getColorStateList(R.color.gris));
                buttonHabilitar.setEnabled(false);
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                },500);

            });

        //Cargar fragment Principal
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedor, infoSala);
        buttonHabilitar.setVisibility(View.VISIBLE);
        buttonFinalizar.setVisibility(View.VISIBLE);
        fragmentTransaction.commit();
    }

    //En este metodo programamos el evento onclick
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Para que cierre la ventana cada ves que selecciono
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.infoSala){
            buttonHabilitar.setVisibility(View.VISIBLE);
            buttonFinalizar.setVisibility(View.VISIBLE);
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, infoSala);
            fragmentTransaction.commit();
        }
        else {
            buttonHabilitar.setVisibility(View.INVISIBLE);
            buttonFinalizar.setVisibility(View.INVISIBLE);
        }


        if (item.getItemId() == R.id.OpcionesVotacion){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, opcionesVotacion);
            fragmentTransaction.commit();

        }
        if (item.getItemId() == R.id.accesoPorNomUsuario){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, addVotanteByUser);
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.accesoPorContraseña){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, accesoContrasenia);
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.accesoPorDni){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, accesoDni);
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.recuentoVoto){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, lista_votos);
            fragmentTransaction.commit();
        }

        return false;
    }
    private void finalizarSala(View view){
        String urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //NUeva peticion JsonObject
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.GET,
                urlBase+"/finalizar/"+salaId, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("FinalizarSala", "Se recupero el json: "+ response);
                Snackbar mensaje = Snackbar.make(view, "Sala finalizada correctamente", Snackbar.LENGTH_LONG);
                mensaje.show();
            }
        }, error -> Log.d("FinalizarSala", "Error Respuesta en Json: " + error.getMessage()));

        // Añadir peticion a la cola
        requestQueue.add(jsArrayRequest);
    }

    private void getSala(){
        String urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //NUeva peticion JsonObject
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.GET,
                urlBase+salaId, null, response -> {
            sala = parseJson(response);
            if(sala.getEstado().equals("FINALIZADA")) {
                buttonFinalizar.setBackgroundTintList(getResources().getColorStateList(R.color.gris));
                buttonFinalizar.setEnabled(false);

                buttonHabilitar.setBackgroundTintList(getResources().getColorStateList(R.color.gris));
                buttonHabilitar.setBackgroundTintList(getResources().getColorStateList(R.color.gris));
                buttonHabilitar.setEnabled(false);
            }{
                if(sala.getEstado().equals("DISPONIBLE")){
                   buttonHabilitar.setBackgroundTintList(getResources().getColorStateList(R.color.gris));
                   buttonHabilitar.setBackgroundTintList(getResources().getColorStateList(R.color.gris));
                   buttonHabilitar.setEnabled(false);
                }
            }
            Log.i("MenuMisSalas", "Se recupero el json de sala:" +response);
        }, error -> Log.d("FinalizarSala", "Error Respuesta en Json: " + error.getMessage()));

        // Añadir peticion a la cola
        requestQueue.add(jsArrayRequest);
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")


    private Sala parseJson(JSONObject jsonObject) {
        //Variables Locales
        Sala sala = new Sala();
        JSONObject jsonSala;
        try {
            //Obtener el array del objeto
            jsonSala = jsonObject.getJSONObject("data");

            sala.setId(jsonSala.getString("id"));
            sala.setNombreSala(jsonSala.getString("nombre"));
            sala.setEstado(jsonSala.getString("estado"));

            Log.i("MenuMisSalas", "Sala recibida");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sala;
    }

    private void habilitarSala(View view){
        requestQueue= Volley.newRequestQueue(this);
        String urlBase = "http://if012hd.fi.mdn.unp.edu.ar:28003/votes-server/rest/salas";
        JSONObject params = new JSONObject();
        try {

            params.put("estado", "DISPONIBLE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObjReq = new JsonObjectRequest(Request.Method.PUT, urlBase+"/estadoSala/"+salaId, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Snackbar mensaje = Snackbar.make(view, "Sala Habilitada correctamente", Snackbar.LENGTH_LONG);
                        mensaje.show();
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