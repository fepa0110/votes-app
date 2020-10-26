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

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.votesapp.R;
import com.example.votesapp.activities.infoSala.InfoSala;
import com.example.votesapp.activities.mis_salas.Sala;
import com.example.votesapp.activities.opciones_votacion.OpcionesVotacion;
import com.example.votesapp.activities.votante_by_dni.AccesoDni;
import com.example.votesapp.activities.votante_by_user.AddVotanteByUser;
import com.google.android.material.navigation.NavigationView;

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

    private int salaId;
    private String nombreSala = " ";
    private String contrasenia = " ";
    private String usernameOwner = null;


    TextView tituloMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mis_salas);

        toolbar= findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);

        salaId = getIntent().getIntExtra("param_id",0);
        nombreSala= getIntent().getStringExtra("param_nombre");
        usernameOwner = getIntent().getStringExtra("param_username");
        contrasenia = getIntent().getStringExtra("param_contrasenia");
//        Log.i("param_nombre",nombreSala);
//        Log.i("param_id",salaId+" ");






        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        //establecer el evento onclick al navigationView
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        tituloMenu=header.findViewById(R.id.tituloMenu);
        tituloMenu.setText(nombreSala);
        toolbar.setTitle(nombreSala);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        Bundle bundle= new Bundle();
        bundle.putInt("param_id",salaId);
        bundle.putString("param_username",usernameOwner);
        bundle.putString("param_contrasenia",contrasenia);

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

        //Cargar fragment Principal
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedor, infoSala);
        fragmentTransaction.commit();
    }

    //En este metodo programamos el evento onclick
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Para que cierre la ventana cada ves que selecciono
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.infoSala){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, infoSala); // aca hiria el fragment o clase de accesoSala
            fragmentTransaction.commit();
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
            fragmentTransaction.replace(R.id.contenedor, addVotanteByUser); // aca hiria el fragment o clase de accesoSala
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.accesoPorContraseña){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, accesoContrasenia); // aca hiria el fragment o clase de accesoSala
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.accesoPorDni){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, accesoDni); // aca hiria el fragment o clase de accesoSala
            fragmentTransaction.commit();
        }

        return false;
    }
}