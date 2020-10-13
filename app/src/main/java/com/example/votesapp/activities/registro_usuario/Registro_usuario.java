package com.example.votesapp.activities.registro_usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.votesapp.R;
import com.example.votesapp.activities.mainActivity.MainActivity;
import com.example.votesapp.activities.newLogin.NewLogin;
import com.example.votesapp.services.RegistroService;

import java.util.regex.Pattern;


public class Registro_usuario extends AppCompatActivity {
    Button siguiente,volver;

    EditText nombreUsuario,apellidoUsuario,correoUsuario,contraUsuario,confirmarContraUsuario;

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);



       //traigo los componentes de la pantalla
        nombreUsuario = (EditText)findViewById(R.id.nombreUsuario);
        apellidoUsuario = (EditText)findViewById(R.id.apellidoUsuario);
        correoUsuario = (EditText)findViewById(R.id.correoUsuario);
        contraUsuario = (EditText)findViewById(R.id.contraseñaUsuario);
        confirmarContraUsuario = (EditText)findViewById(R.id.confirmarContraseñaUsuario);
        final EditText[] misCampos = {nombreUsuario,apellidoUsuario,correoUsuario,contraUsuario,confirmarContraUsuario};
        siguiente =(Button)findViewById(R.id.botonSiguiente);
        volver = (Button)findViewById(R.id.botonVolver);



        //al hacer click en nombre
        nombreUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreUsuario.setError(null);
            }
        });

        //Al hacer click en apellido
        apellidoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apellidoUsuario.setError(null);
            }
        });

        //Al hacer click en correo Electronico
        correoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=correoUsuario.getText().toString();
                if(checkEmail(email)) {
                    //Toast.makeText(getApplicationContext(), "Valid Email Addresss", Toast.LENGTH_SHORT).show();
                    hideSoftKeyboard();

                }else
                    //Toast.makeText(getApplicationContext(),"Invalid Email Addresss", Toast.LENGTH_SHORT).show();
                    correoUsuario.setError("Correo no valido");
            }
        });

        //Al hacer click en contraseña
        contraUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contraUsuario.setError(null);

            }
        });
        confirmarContraUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarContraUsuario.setError(null);
            }
        });

        //Al hacer click en el boton volver
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volver = new Intent(Registro_usuario.this, NewLogin.class);
                startActivity(volver);
                finish();
            }
        });

        //Al hacer click en el boton siguiente
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombreUsuario.getText().toString().isEmpty()){
                    nombreUsuario.setError("Este campo no puede estar vacio");
                }else {
                    if (apellidoUsuario.getText().toString().isEmpty()){
                        apellidoUsuario.setError("Este campo no puede estar vacio");
                    }else{
                        if (correoUsuario.getText().toString().isEmpty()){
                            correoUsuario.setError("Este campo no puede estar vacio");
                        }else{
                            if (contraUsuario.getText().toString().isEmpty()){
                                contraUsuario.setError("Este campo no puede estar vacio");
                            }else {
                                if (confirmarContraUsuario.getText().toString().isEmpty()){
                                    confirmarContraUsuario.setError("Este campo no puede estar vacio");
                                }else {
                                    Intent siguiente = new Intent(Registro_usuario.this,Registro_usuario2.class);
                                    siguiente.putExtra("nombre",nombreUsuario.getText().toString());
                                    siguiente.putExtra("apellido",apellidoUsuario.getText().toString());
                                    siguiente.putExtra("correo",correoUsuario.getText().toString());
                                    siguiente.putExtra("contrasenia",contraUsuario.getText().toString());
                                    startActivity(siguiente);

                                }
                            }
                        }
                    }
                }

//                if (validarCamposVacios(misCampos))
//
//
//                    Toast.makeText(Registro_usuario.this,"Faltan campos por llenar",Toast.LENGTH_SHORT).show();
//                else{
//                        Intent siguiente = new Intent(Registro_usuario.this,Registro_usuario2.class);
//                        siguiente.putExtra("nombre",nombreUsuario.getText().toString());
//                        siguiente.putExtra("apellido",apellidoUsuario.getText().toString());
//                        siguiente.putExtra("correo",correoUsuario.getText().toString());
//                        siguiente.putExtra("contrasenia",contraUsuario.getText().toString());
//                        startActivity(siguiente);
//                }
            }
        });

    }
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

//    public boolean validarCamposVacios(EditText[] campos){
//        for (int i=0;i<campos.length; i++){
//            String cadena = campos[i].getText().toString();
//            if(cadena.trim().isEmpty()){
//
//                return true;
//            }
//
//        }
//        return false;
//    }
//    private boolean validarEmail(String email) {
//        Pattern pattern = Patterns.EMAIL_ADDRESS;
//        return pattern.matcher(email).matches();
//    }

    private boolean validarCorreo(String correo){
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(correo).matches();
    }

//    //metodo para ejecutarlo al presionar el boton
//    public void Enviar(View view){
//        if(nombreUsuario.getText().toString().isEmpty()){
//            nombreUsuario.setError("Este campo no puede estar vacio");
//
//        }
//    }


    private void hideSoftKeyboard(){
        if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(correoUsuario.getWindowToken(), 0);
        }
    }






}