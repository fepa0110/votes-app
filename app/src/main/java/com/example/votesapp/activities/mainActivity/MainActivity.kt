package com.example.votesapp.activities.mainActivity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.votesapp.R
import com.example.votesapp.activities.historial.Historial
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
//                setSupportActionBar(findViewById(R.id.toolbar))
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        this.title = "Aplicacion de votaciones"

        username = intent.getStringExtra("param_username")
        userNombre = intent.getStringExtra("param_usuario_nombre")

        //Welcome Fragment
        val welcomeFragment = WelcomeFragment.newInstance()
        welcomeFragment.activity?.intent?.putExtra("param_usuario_nombre", userNombre)
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
                    val misSalasFragment = MisSalas.newInstance()

                    misSalasFragment.activity?.intent?.putExtra("param_username",username)

                    openFragment(misSalasFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_salas -> {
                    this.title = "Salas"
                    val salasFragment = Lista_Salas.newInstance()

                    salasFragment.activity?.intent?.putExtra("param_username",username)

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
    //Karen
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.historialSalasFinal) {
            val historialFragment = Historial.newInstance()

            historialFragment.activity?.intent?.putExtra("param_username", username)
            openFragment(historialFragment)

            true
        } else super.onOptionsItemSelected(item)
    }
    //Karen

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