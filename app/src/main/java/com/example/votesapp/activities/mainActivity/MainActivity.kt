package com.example.votesapp.activities.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.votesapp.R
import com.example.votesapp.activities.lista_salas.Lista_Salas

<<<<<<< HEAD
import com.example.votesapp.activities.crear_sala.CrearSala
import com.example.votesapp.activities.registro_usuario.Registro_usuario

import android.content.Intent as Intent
=======
import com.example.votesapp.activities.mis_salas.MisSalas
import com.google.android.material.bottomnavigation.BottomNavigationView
>>>>>>> master

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        this.title = "Aplicacion de votaciones"

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    //Escuchador de clicks de los botones del menu inferior
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item ->
            when (item.itemId) {
                R.id.navigation_mis_salas -> {
                    this.title = "Mis salas"
                    val salasFragment = MisSalas.newInstance()
                    openFragment(salasFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_salas -> {
                    this.title = "Salas"
                    val salasFragment = Lista_Salas.newInstance()
                    openFragment(salasFragment)
                    return@OnNavigationItemSelectedListener true
                }
                /* R.id.navigation_artists -> {

                    return@OnNavigationItemSelectedListener true
                }*/
            }
        false
    }

<<<<<<< HEAD


    fun openVerSalas(view: View){
        val intert2 = Intent(this,Registro_usuario::class.java);
        startActivity(intert2);
=======
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
>>>>>>> master
    }
}