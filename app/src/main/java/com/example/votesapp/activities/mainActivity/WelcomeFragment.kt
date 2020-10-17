package com.example.votesapp.activities.mainActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.votesapp.R
import com.example.votesapp.activities.lista_salas.Lista_Salas
import com.example.votesapp.activities.lista_salas.SalaAdapter

class WelcomeFragment : Fragment() {
    private var userNombre : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewFragment = inflater.inflate(R.layout.fragment_welcome, container, false)
        var usernameTextView = viewFragment.findViewById<TextView>(R.id.welcome_textView_username)

        userNombre = this.activity?.intent?.getStringExtra("param_usuario_nombre")
        usernameTextView.text = userNombre

        return viewFragment
    }

    companion object {
        fun newInstance(): WelcomeFragment = WelcomeFragment()
    }
}