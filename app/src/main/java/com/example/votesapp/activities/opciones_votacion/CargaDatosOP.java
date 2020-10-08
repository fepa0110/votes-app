package com.example.votesapp.activities.opciones_votacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votesapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class CargaDatosOP extends AppCompatActivity {

    private TextInputEditText titulo, descripcion;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_datos_op);

        titulo = findViewById(R.id.Text_Titulo_opVotacion);
        titulo.setText(getIntent().getStringExtra("param_titulo"));
        titulo.setFocusable(getIntent().getBooleanExtra("param_editable", false));
        titulo.setLongClickable(getIntent().getBooleanExtra("param_editable", false));
        titulo.setCursorVisible(getIntent().getBooleanExtra("param_editable", false));
        titulo.setFocusableInTouchMode(getIntent().getBooleanExtra("param_editable", false));

        descripcion = findViewById(R.id.Text_Descripcion_opVotacion);
        descripcion.setText(getIntent().getStringExtra("param_descripcion"));

        //Boton Cancelar
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Como retorno?? 
            }
        });

    }
}