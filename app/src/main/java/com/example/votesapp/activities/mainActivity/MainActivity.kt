package com.example.votesapp.activities.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.votesapp.R
import com.example.votesapp.activities.lista_salas.Lista_Salas

import com.example.votesapp.activities.mis_salas.MisSalas
import com.example.votesapp.activities.user_activity.UserActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var username : String? = null
    private var userNombre : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        this.title = "Aplicacion de votaciones"

        username = intent.getStringExtra("param_username")
        userNombre = intent.getStringExtra("param_usuario_nombre")

        //Welcome Fragment
        val welcomeFragment = WelcomeFragment.newInstance()
        welcomeFragment.activity?.intent?.putExtra("param_usuario_nombre",userNombre)
        openFragmentNoBackStack(welcomeFragment)

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

                    salasFragment.activity?.intent?.putExtra("param_username",username)

                    openFragment(salasFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_salas -> {
                    this.title = "Salas"
                    val salasFragment = Lista_Salas.newInstance()
                    openFragment(salasFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_usuario -> {
                    this.title = "Perfil de usuario"
                    val userFragment = UserActivity.newInstance()

                    userFragment.activity?.intent?.putExtra("param_username",username)

                    openFragment(userFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun openFragmentNoBackStack(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }
}