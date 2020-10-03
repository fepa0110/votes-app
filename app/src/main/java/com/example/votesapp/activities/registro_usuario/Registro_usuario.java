package com.example.votesapp.activities.registro_usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votesapp.R;

public class Registro_usuario extends AppCompatActivity {
    Button siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        siguiente =(Button)findViewById(R.id.botonSiguiente);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siguiente = new Intent(Registro_usuario.this,Registro_usuario2.class);
                startActivity(siguiente);

            }
        });


    }

}