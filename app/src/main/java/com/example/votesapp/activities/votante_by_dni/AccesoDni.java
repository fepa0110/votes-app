package com.example.votesapp.activities.votante_by_dni;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.votesapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class AccesoDni extends Fragment {

    private ListView listView;
    private int salaId;
    private String nombreUsuario;
    private AccesoDniAdapter dniAdapter;
    private Button btnAñadir, btnGuardar;
    private TextInputEditText inputDni;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_acceso_dni, container, false);

        //Parametros
        this.salaId = getArguments().getInt("param_id");
        this.nombreUsuario = getArguments().getString("param_username");

        listView = view.findViewById(R.id.add_votante_dni_listView);

        dniAdapter = new AccesoDniAdapter(view.getContext(),salaId);

        listView.setAdapter(dniAdapter);

        inputDni = view.findViewById(R.id.text_input_dni);


        btnAñadir = view.findViewById(R.id.button_añadir_dni);
        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dni = inputDni.getText().toString();
                if (dni.length() != 8){
                    inputDni.setError("El numero de DNI debe ser igual a 8 digitos");
                }else{
                    if(dniAdapter.buscarDni(dni)){
                        inputDni.setError("El DNI ya se encuentra en la lista");
                    }else {
                        dniAdapter.addDni(inputDni.getText().toString());
                        inputDni.setError(null);
                    }
                }

            }
        });

        btnGuardar = view.findViewById(R.id.button_guardar_dni);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dniAdapter.sendVotantesByDni();
            }
        });

        return view;

    }
}