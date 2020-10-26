package com.example.votesapp.activities.lista_salas;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.votesapp.R;
import com.example.votesapp.activities.menuMisSalas.MenuMisSalas;
import com.example.votesapp.activities.menuSala.MenuSala;
import com.example.votesapp.activities.opciones_votacion.CargaDatosOP;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DialogoContra {



    public DialogoContra (Context context,String contraseniaSala,String id,String nombreSala,String estado,String username){

        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        //dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.));
        dialogo.setContentView(R.layout.fragment_dialogo_contra_sala);



        final TextInputLayout contraLayout = (TextInputLayout) dialogo.findViewById(R.id.contrasenia_dialogo);
        final TextInputEditText contraDialog = (TextInputEditText) dialogo.findViewById(R.id.text_contrasenia_dialogo);
        final Button ingresar = (Button)dialogo.findViewById(R.id.boton_ingresar);
        final Button cancelar = (Button)dialogo.findViewById(R.id.boton_cancelar);

        //contraDialog.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contraDialog.getText().toString().contentEquals(contraseniaSala)) {
                    Intent intent = new Intent(context, MenuSala.class);
                    intent.putExtra("param_id",id);
                    intent.putExtra("param_nombre",nombreSala);
                    intent.putExtra("param_estado",estado);
                    intent.putExtra("param_username",username);

                    context.startActivity(intent);
                    dialogo.dismiss();

                }contraLayout.setError("Contraseña incorrecta");
            }



        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }
}
