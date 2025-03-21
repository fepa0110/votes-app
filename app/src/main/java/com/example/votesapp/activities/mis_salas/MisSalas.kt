package com.example.votesapp.activities.mis_salas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.votesapp.R
import com.example.votesapp.activities.crear_sala.CrearSala
import com.example.votesapp.activities.lista_salas.Sala
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MisSalas : Fragment() {
    //Atributos
    private var slistView: ListView? = null

    //Llamamos el adapter
    private var sAdapter: ListAdapter? = null

    private var username : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewFragment = inflater.inflate(R.layout.activity_listado_mis_salas, container, false)
        slistView = viewFragment.findViewById(R.id.listView_mis_salas)
        val toolbar = viewFragment.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)


        val buttonCrearSala = viewFragment.findViewById<FloatingActionButton>(R.id.button_crear_sala)
        buttonCrearSala.setOnClickListener {
            val intent = Intent(activity, CrearSala::class.java)
            intent.putExtra("param_username",username)
            startActivity(intent)  //Ejecutar salto a la actividad
        }

        username = this.activity?.intent?.getStringExtra("param_username")

        //inicializamos el adaptador que va a poner la lista de sala
        sAdapter = SalaAdapter(activity,"user/$username/",username!!)

        //Crear adaptador y setear
        slistView?.adapter = sAdapter

        return viewFragment
    }

    companion object {
        fun newInstance(): MisSalas = MisSalas()
    }

    override fun onResume() {
        super.onResume()
        username = this.activity?.intent?.getStringExtra("param_username")
        //setear
        slistView?.adapter = null
        sAdapter = null
        //inicializamos el adaptador que va a poner la lista de sala
        sAdapter = SalaAdapter(activity, "user/$username/",username!!)
        //Crear adaptador y setear
        slistView?.adapter = sAdapter
    }
}