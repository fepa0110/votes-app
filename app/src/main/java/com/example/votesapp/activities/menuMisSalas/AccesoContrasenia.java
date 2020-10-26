package com.example.votesapp.activities.menuMisSalas;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.votesapp.R;
import com.example.votesapp.activities.mis_salas.Sala;
import com.example.votesapp.services.ContraseniaService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class AccesoContrasenia extends Fragment {

    private TextInputLayout layoutAccesoContrasenia;
    private TextInputLayout layoutConfirmarContrasenia;
    private Button aceptarContra;

    private TextInputEditText text_accesoContrasenia;
    private TextInputEditText text_accesoConfirmarContrasenia;
    private int id;
    private String contrasenia;
    ContraseniaService contraseniaService = new ContraseniaService();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_acceso_contrasenia,container,false);

        layoutAccesoContrasenia = (TextInputLayout) view.findViewById(R.id.acceder_sala_contrasenia);
        layoutConfirmarContrasenia = (TextInputLayout)view.findViewById(R.id.acceso_sala_confirmar_contrasenia);
        text_accesoContrasenia  = (TextInputEditText)view.findViewById(R.id.text_acceder_sala_contrasenia);
        text_accesoConfirmarContrasenia = (TextInputEditText)view.findViewById(R.id.text_confirmarAccesoContrasenia);
        aceptarContra = (Button)view.findViewById(R.id.botonAceptarContrasenia);

        this.id = getArguments().getInt("param_id");
        this.contrasenia = getArguments().getString("param_contrasenia");



        if(!contrasenia.toString().equals("null")){
            text_accesoContrasenia.setText(contrasenia);
            layoutConfirmarContrasenia.setVisibility(View.INVISIBLE);
            text_accesoConfirmarContrasenia.setVisibility(View.INVISIBLE);
            aceptarContra.setVisibility(View.INVISIBLE);
            text_accesoContrasenia.setEnabled(false);


        }





        text_accesoContrasenia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                escontraseniaValida(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        text_accesoConfirmarContrasenia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                esConfirmarContraseñaValida(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        aceptarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDatos();
            }
        });






        return view;
    }

    //Validar la contraseña
    private boolean escontraseniaValida(String contrasenia){
        Pattern patron = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$");
        if (!patron.matcher(contrasenia).matches()) {
            layoutAccesoContrasenia.setError("La contraseña debe tener entre 8 y 16 caracteres, al menos un digito, al menos una minuscula y al menos una mayuscula");
            return false;
        } else {
            layoutAccesoContrasenia.setError(null);
        }
        return true;
    }

    //Metodo para confirmar la contraseña
    private boolean esConfirmarContraseñaValida(String confirmar){
        //Pattern patron = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$");
        String y = text_accesoContrasenia.getText().toString();

        if (confirmar.equals(y) ) {
            layoutConfirmarContrasenia.setError(null);

            return true;
        } else {
            layoutConfirmarContrasenia.setError("Las contraseña no son iguales, vuelva a intentarlo");
        }
        return false;
    }

    private void validarDatos() {



        String contrasenia = layoutAccesoContrasenia.getEditText().getText().toString();
        String confirmar = layoutConfirmarContrasenia.getEditText().getText().toString();

        boolean a = escontraseniaValida(contrasenia);
        boolean b = esConfirmarContraseñaValida(confirmar);
        if (a && b ) {
            Sala sala = new Sala();
            sala.setContrasenia(text_accesoContrasenia.getText().toString());
            this.createContraseña(text_accesoContrasenia.getText().toString());

        }

    }

    private void createContraseña(String contrasenia){
        contraseniaService.create(getContext(),contrasenia,id);

    }


}
