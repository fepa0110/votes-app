package com.example.votesapp.activities.crear_sala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.votesapp.R
import com.example.votesapp.activities.mainActivity.MainActivity

class CrearSala : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sala)
    }

    fun backToMain(view: View){
        val intent = Intent(this,MainActivity::class.java);
        startActivity(intent);
    }
}