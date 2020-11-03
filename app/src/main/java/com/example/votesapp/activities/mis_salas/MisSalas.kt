package com.example.votesapp.activities.mis_salas

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.votesapp.R
import com.example.votesapp.activities.crear_sala.CrearSala
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MisSalas : Fragment() {
    //Atributos
    private var slistView: ListView? = null

    //Llamamos el adapter
    private var sAdapter: ListAdapter? = null

    private var username : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewFragment = inflater.inflate(R.layout.activity_listado_mis_salas, container, false)
        slistView = viewFragment.findViewById(R.id.listView_mis_salas)

        val buttonCrearSala = viewFragment.findViewById<FloatingActionButton>(R.id.button_crear_sala)
        buttonCrearSala.setOnClickListener {
            val intent = Intent(activity, CrearSala::class.java)
            intent.putExtra("param_username", username)
            startActivity(intent)  //Ejecutar salto a la actividad
        }

        username = this.activity?.intent?.getStringExtra("param_username")

        //inicializamos el adaptador que va a poner la lista de sala
        sAdapter = SalaAdapter(activity, "user/$username/", username!!)

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
        sAdapter = SalaAdapter(activity, "user/$username/", username!!)
        //Crear adaptador y setear
        slistView?.adapter = sAdapter
    }

//    fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu)
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}