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
import com.google.android.material.textfield.TextInputLayout;

public class AccesoDni extends Fragment {

    private ListView listView;
    private int salaId;
    private String nombreUsuario;
    private String estado;
    private AccesoDniAdapter dniAdapter;
    private Button btnAñadir, btnGuardar;
    private TextInputEditText inputDni;
    private TextInputLayout textInputLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_acceso_dni, container, false);

        //Parametros
        this.salaId = getArguments().getInt("param_id");
        this.nombreUsuario = getArguments().getString("param_username");
        this.estado = getArguments().getString("param_estado");

        listView = view.findViewById(R.id.add_votante_dni_listView);

        dniAdapter = new AccesoDniAdapter(view.getContext(),salaId);

        listView.setAdapter(dniAdapter);

        inputDni = view.findViewById(R.id.text_input_dni);
        textInputLayout = view.findViewById(R.id.textInputLayout_dni);

        btnAñadir = view.findViewById(R.id.button_añadir_dni);
        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dni = inputDni.getText().toString();
                if(dni.isEmpty()){
                    textInputLayout.setError("El campo no puede estar vacio");
                }else {
                    if (dni.length() != 8) {
                        textInputLayout.setError("El numero de DNI debe ser igual a 8 digitos");
                    } else {
                        if (dni.equals("00000000")){
                            textInputLayout.setError("DNI invalido");
                        }else {
                            if (dniAdapter.buscarDni(dni)) {
                                textInputLayout.setError("El DNI ya se encuentra en la lista");
                            } else {
                                dniAdapter.addDni(inputDni.getText().toString());
                                textInputLayout.setError(null);
                                inputDni.setText("");
                            }
                        }
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

        //Deshabilitar nuevos ingresos si esta finalizada la sala
        if(estado.equals("FINALIZADA")){
            inputDni.setEnabled(false);
            btnAñadir.setEnabled(false);
            btnAñadir.setBackground(getResources().getDrawable(R.drawable.button_circle_disabled,null));

            btnGuardar.setEnabled(false);
            btnGuardar.setBackground(getResources().getDrawable(R.drawable.button_circle_disabled,null));
        }

        return view;

    }
}