package com.example.votesapp.activities.menuSala;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.votesapp.R;
import com.example.votesapp.activities.infoSala.InfoSala;
import com.example.votesapp.activities.opciones_votacion.OpcionesVotacion;
import com.example.votesapp.activities.votoTotal.Lista_votos;
import com.google.android.material.navigation.NavigationView;

public class MenuSala extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    private int salaId;
    private String nombreSala = " ";
    private String usernameOwner = null;
    private String estado =" ";
    TextView tituloMenu;

    //variables para cargar el fragment principal
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private  OpcionesVotacion opcionesVotacion;
    private  InfoSala infoSala;
    private Lista_votos lista_votos;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_salas);
        toolbar= findViewById(R.id.toolbar_menu2);
        setSupportActionBar(toolbar);

        salaId = getIntent().getIntExtra("param_id",0);
        nombreSala= getIntent().getStringExtra("param_nombre");
        usernameOwner = getIntent().getStringExtra("param_username");
        estado = getIntent().getStringExtra("param_estado");

        drawerLayout = findViewById(R.id.drawer2);
        navigationView = findViewById(R.id.navigationView2);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        View header = navigationView.getHeaderView(0);
        tituloMenu=header.findViewById(R.id.tituloMenu2);
        tituloMenu.setText(nombreSala);
        toolbar.setTitle(nombreSala);

        Bundle bundle= new Bundle();
        bundle.putInt("param_id",salaId);
        bundle.putString("param_username",usernameOwner);
        bundle.putBoolean("param_desde_salas",true);
        bundle.putString("param_estado",estado);

        infoSala = new InfoSala();
        infoSala.setArguments(bundle);

        opcionesVotacion = new OpcionesVotacion();
        opcionesVotacion.setArguments(bundle);

        lista_votos = new Lista_votos();
        lista_votos.setArguments(bundle);


        //Cargar fragment Principal
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedor2, infoSala);
        fragmentTransaction.commit();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Para que cierre la ventana cada ves que selecciono
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.infoSala2){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor2, infoSala); // aca hiria el fragment o clase de accesoSala
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.OpcionesVotacion2){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor2, opcionesVotacion);
            fragmentTransaction.commit();

        }
        if (item.getItemId() == R.id.recuentoVoto) {
            Log.i("estadoooooo",estado);
            if (estado.equals("FINALIZADA")) {

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenedor2, lista_votos);
                fragmentTransaction.commit();

            }
        }

        return false;
    }
}


