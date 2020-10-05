package com.example.votesapp.activities.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.votesapp.R
import com.example.votesapp.activities.lista_salas.Lista_Salas

import com.example.votesapp.activities.mis_salas.MisSalas
import com.google.android.material.bottomnavigation.BottomNavigationView

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

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}