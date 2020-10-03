package com.example.votesapp.activities.mis_salas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.votesapp.R
import com.example.votesapp.activities.crear_sala.CrearSala
import com.example.votesapp.activities.lista_salas.Sala
import java.util.*

class MisSalas : AppCompatActivity() {
    //Atributos
    private var slistView: ListView? = null

    //Llamamos el adapter
    private var sAdapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_mis_salas)
        setSupportActionBar(findViewById(R.id.toolbar))
        slistView = findViewById(R.id.listView)

        //!USUARIO HARDCODEADO
        val username = "Hardcodeado"

        //inicializamos el adaptador que va a poner la lista de sala
        sAdapter = SalaAdapter(this,"user/$username/")

        //Crear adaptador y setear
        slistView?.adapter = sAdapter
    }

    fun openCrearSala(view: View){
        //Crear vinculo entre esta actividad y CrearSala
        val intent = Intent(this, CrearSala::class.java);
        startActivity(intent)  //Ejecutar salto a la actividad
    }
}