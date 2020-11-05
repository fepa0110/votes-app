package com.example.votesapp.activities.historial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.votesapp.R
import com.example.votesapp.activities.lista_salas.Lista_Salas
import com.example.votesapp.activities.lista_salas.SalaAdapter

class Historial : Fragment() {

    private var slistView: ListView? = null

    //Llamamos el adapter
    private var sAdapter: ListAdapter? = null

    private var username : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewFragment = inflater.inflate(R.layout.listado_historial, container, false)
        slistView = viewFragment.findViewById(R.id.listahistorial)

        username = this.activity?.intent?.getStringExtra("param_username")

        //inicializamos el adaptador que va a poner la lista de sala

        sAdapter = SalaAdapterFinalizada(activity,"user/$username/",username!!)

        //Crear adaptador y setear
        slistView?.adapter = sAdapter

        return viewFragment
    }

    companion object {
        fun newInstance(): Historial = Historial()
    }
}