package com.example.votesapp.activities.registro_usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.votesapp.R;

import java.util.regex.Pattern;


public class Registro_usuario extends AppCompatActivity {
    Button siguiente;

    EditText nombreUsuario,apellidoUsuario,correoUsuario,contraUsuario,confirmarContraUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        siguiente =(Button)findViewById(R.id.botonSiguiente);

//        siguiente.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent siguiente = new Intent(Registro_usuario.this,Registro_usuario2.class);
//                startActivity(siguiente);
//
//
//            }
//        });

        //traigo los componentes de la pantalla

        nombreUsuario = (EditText)findViewById(R.id.nombreUsuario);
        apellidoUsuario = (EditText)findViewById(R.id.apellidoUsuario);
        correoUsuario = (EditText)findViewById(R.id.correoUsuario);
        contraUsuario = (EditText)findViewById(R.id.contraseñaUsuario);
        confirmarContraUsuario = (EditText)findViewById(R.id.confirmarContraseñaUsuario);
        final EditText[] misCampos = {nombreUsuario,apellidoUsuario,correoUsuario,contraUsuario,confirmarContraUsuario};

        correoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCorreo(correoUsuario.getText().toString().trim())){

                    Toast.makeText(Registro_usuario.this,"Correo electronico correcto",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Registro_usuario.this,"Correo electronico incorrecto",Toast.LENGTH_SHORT).show();
                }

            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCamposVacios(misCampos))
                    Toast.makeText(Registro_usuario.this,"Faltan campos por llenar",Toast.LENGTH_SHORT).show();
                else{
                        Intent siguiente = new Intent(Registro_usuario.this,Registro_usuario2.class);
                        siguiente.putExtra("nombre",nombreUsuario.getText().toString());
                        siguiente.putExtra("apellido",apellidoUsuario.getText().toString());
                        siguiente.putExtra("correo",correoUsuario.getText().toString());
                        siguiente.putExtra("contrasenia",contraUsuario.getText().toString());
                        startActivity(siguiente);
                }
            }
        });

    }

    public boolean validarCamposVacios(EditText[] campos){
        for (int i=0;i<campos.length; i++){
            String cadena = campos[i].getText().toString();
            if(cadena.trim().isEmpty()){
                return true;
            }

        }
        return false;
    }

    private boolean validarCorreo(String correo){
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(correo).matches();
    }


//    public boolean validar() {
//        boolean retorno = true;
//        String c1 = nombreUsuario.getText().toString();
//        String c2 = apellidoUsuario.getText().toString();
//        String c3 = correoUsuario.getText().toString();
//        String c4 = contraUsuario.getText().toString();
//        String c5 = confirmarContraUsuario.getText().toString();
//
//
//        if (c1.isEmpty()){
//            nombreUsuario.setError("Este campo1 no puede quedar vacio");
//            retorno=false;
//        }
//        if (c2.isEmpty()){
//            apellidoUsuario.setError("Este campo2 no puede quedar vacio");
//            retorno=false;
//        }if (c3.isEmpty()){
//            correoUsuario.setError("Este campo3 no puede quedar vacio");
//            retorno=false;
//        }if (c4.isEmpty()){
//            contraUsuario.setError("Este campo4 no puede quedar vacio");
//            retorno=false;
//        }if (c5.isEmpty())
//            confirmarContraUsuario.setError("Este campo5 no puede quedar vacio");
//            retorno=false;
//
//
//
//
//
//        return retorno;
//    }






}