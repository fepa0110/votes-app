package com.example.votesapp.activities.lista_salas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.votesapp.R;

import java.util.ArrayList;
import java.util.List;

public class Lista_Salas extends AppCompatActivity {

    //Atributos
    private ListView slistView;
    List<Sala> sLista = new ArrayList<>();

    //Llamamos el adapter
    ListAdapter sAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_salas);

        //Obtener instancia de la lista
        slistView = findViewById(R.id.listView);


        //inicializamos el adaptador que va a poner la lista de sala
        sAdapter = new SalaAdapter(this);

        //Crear adaptador y setear
        slistView.setAdapter(sAdapter);


    }


}