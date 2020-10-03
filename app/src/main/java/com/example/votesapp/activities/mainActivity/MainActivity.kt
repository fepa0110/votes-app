package com.example.votesapp.activities.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.votesapp.R
import com.example.votesapp.activities.lista_salas.Lista_Salas

import com.example.votesapp.activities.crear_sala.CrearSala
import com.example.votesapp.activities.registro_usuario.Registro_usuario

import android.content.Intent as Intent

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Metodo para ir a la actividad de crear sala
     */
    fun openCrearSala(view: View){
        //Crear vinculo entre esta actividad y CrearSala
        val intent = Intent(this, CrearSala::class.java);
        startActivity(intent);  //Ejecutar salto a la actividad
    }



    fun openVerSalas(view: View){
        val intert2 = Intent(this,Registro_usuario::class.java);
        startActivity(intert2);
    }
}