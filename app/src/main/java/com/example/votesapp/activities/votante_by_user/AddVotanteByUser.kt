package com.example.votesapp.activities.votante_by_user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.votesapp.R
import com.example.votesapp.activities.crear_sala.CrearSala
import com.example.votesapp.activities.mis_salas.MisSalas
import com.example.votesapp.activities.mis_salas.SalaAdapter
import com.example.votesapp.model.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddVotanteByUser : Fragment() {
    //Atributos
    private var usuariosListView: ListView? = null

    //Llamamos el adapter
    private var votanteByUsernameAdapter: VotanteByUsernameAdapter? = null

    companion object {
        fun newInstance(): AddVotanteByUser = AddVotanteByUser()
    }

    private var salaId : Int? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewFragment = inflater.inflate(R.layout.activity_add_votante_by_user, container, false)

        this.salaId = arguments?.getInt("param_id")

        usuariosListView = viewFragment.findViewById(R.id.add_votante_username_listView)

        votanteByUsernameAdapter = VotanteByUsernameAdapter(viewFragment.context,salaId!!)

        //Crear adaptador y setear
        usuariosListView?.adapter = votanteByUsernameAdapter

        val editTextAgregarVotante = viewFragment.findViewById<EditText>(R.id.add_votante_editText)
        val agregarVotanteButton = viewFragment.findViewById<FloatingActionButton>(R.id.add_votante_button)
        agregarVotanteButton.setOnClickListener {
            votanteByUsernameAdapter!!.addUser(Usuario(editTextAgregarVotante.text.toString()))
            //Llamar aca a verificar si usuario existe
            editTextAgregarVotante.text.clear()
        }

        val finalizarButton = viewFragment.findViewById<Button>(R.id.add_votante_button_finalizar)
        finalizarButton.setOnClickListener {
            votanteByUsernameAdapter!!.sendVotantes()
        }

        return viewFragment
    }

    fun isUserExist(){

    }
}