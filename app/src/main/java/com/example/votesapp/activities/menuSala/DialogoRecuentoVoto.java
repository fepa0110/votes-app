package com.example.votesapp.activities.menuSala;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.votesapp.R;
import com.example.votesapp.activities.infoSala.InfoSala;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DialogoRecuentoVoto {

    public DialogoRecuentoVoto (Context context){

        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        //dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.));
        dialogo.setContentView(R.layout.fragment_dialogo_recuento_voto);

        final Button ok = (Button)dialogo.findViewById(R.id.boton_ok);

        //contraDialog.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dialogo.dismiss();

            }
        });

        dialogo.show();
    }
}
