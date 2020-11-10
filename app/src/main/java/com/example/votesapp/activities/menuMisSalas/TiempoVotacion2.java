package com.example.votesapp.activities.menuMisSalas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.votesapp.R;
import com.example.votesapp.services.DuracionService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TiempoVotacion2 extends Fragment {
    private int id;
    private Button guardarDuracion;
    private TextInputLayout fechaLayout, horaLayout;
    private TextInputEditText textTiempoVotacion, textTiempoHora;
    DuracionService duracionService = new DuracionService();
    final Calendar getDate = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tiempo_votacion, container, false);

        this.id = getArguments().getInt("param_id");

//    if(!textTiempoHora.toString().equals("null")){
//        textTiempoVotacion.setText(textTiempoVotacion.getEditableText());
//        textTiempoHora.setText(textTiempoHora.getEditableText());
//        fechaLayout.setVisibility(View.INVISIBLE);
//        horaLayout.setVisibility(View.INVISIBLE);
//        guardarDuracion.setVisibility(View.INVISIBLE);
//
//    }

        fechaLayout = (TextInputLayout) view.findViewById(R.id.fecha);
        horaLayout = (TextInputLayout) view.findViewById(R.id.hora);

        guardarDuracion = (Button) view.findViewById(R.id.boton_guardar_tiempo_votacion);
        textTiempoVotacion = (TextInputEditText) view.findViewById(R.id.text_tiempo_votacion);
        textTiempoHora = (TextInputEditText) view.findViewById(R.id.text_tiempo_hora);

        horaLayout.setEnabled(false);
//        if (!textTiempoVotacion.getText().toString().equals(null)){
//            horaLayout.setEnabled(false);
//        }

        textTiempoVotacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        textTiempoHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        guardarDuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDatos();
            }
        });

        textTiempoVotacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                esCorrectoFecha(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        textTiempoHora.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                esCorrectoHora(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        return view;
    }

    private void showTimePickerDialog() {

        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String selectedTime;

//                getDate.set(Calendar.HOUR_OF_DAY, hour);
//                getDate.set(Calendar.MINUTE, minute);
//                SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm a");
//                String formatedDate = timeformat.format(getDate.getTime());
//                textTiempoHora.setText(formatedDate);
//
//                String AM_PM;
//                if (hour < 12) {
//                    AM_PM = "a.m.";
//                } else {
//                    AM_PM = "p.m.";
//                }

                Log.i("fecha hoy",textTiempoVotacion.getText().toString());
                String fechaActual = getDate.get(Calendar.YEAR)+"-"+(getDate.get(Calendar.MONTH)+1)+"-"+getDate.get(Calendar.DAY_OF_MONTH);
                String horaActual = getDate.get(Calendar.HOUR)+":"+getDate.get((Calendar.MINUTE));
                Log.i("fechaActual",fechaActual);
                if (fechaActual.equals(textTiempoVotacion.getText().toString())){
                    if (hour > getDate.get(Calendar.HOUR)){
                        if (minute>getDate.get((Calendar.MINUTE))){
                            selectedTime = hour + ":" + minute;
                            textTiempoHora.setText(selectedTime);

                        }else {
                            textTiempoHora.setText(null);
                            horaLayout.setError("Los minutos deben ser mayor al actual");

                        }
                    }else {
                        textTiempoHora.setText(null);
                        horaLayout.setError("La hora debe ser mayor al actual");

                    }
                }else {
                    selectedTime = hour + ":" + minute;
                    textTiempoHora.setText(selectedTime);
                }
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }

    private void showDatePickerDialog() {
        String birthDate = textTiempoVotacion.getText().toString();
        DatePickerFragment2 newFragment = DatePickerFragment2.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                String selectedDate = year + "-" + (month + 1) + "-" + day;
                // +1 because January is zero
                textTiempoVotacion.setText(selectedDate);
            }

        });

//        if (birthDate.isEmpty()) {
//            DatePickerFragment2 datePickerFragment2 = DatePickerFragment2.newInstance( DatePickerDialog.OnDateSetListener
//                    (,year,  month,  day)->{
//                String selectedDate = year + "-" + (month + 1) + "-" + day;
//
//
//                // +1 because January is zero
//                textTiempoVotacion.setText(selectedDate);
//
//            }
//
//
//            ;
//        }else {
//            List<String> parts = Arrays.asList(birthDate.split("-"));
//            for (int i = 0; i<parts.size();i++){
//                Log.i("parts " + i,parts.get(i));
//            }
//            DatePickerFragment2.newInstance(new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                    String selectedDate = year + "-" + (month + 1) + "-" + day;
//
//                    // +1 because January is zero
//                    textTiempoVotacion.setText(selectedDate);
//                    Log.i("selectedDAte",selectedDate);
//                }
//            },Integer.valueOf(parts.get(0)),Integer.valueOf(parts.get(1)),Integer.valueOf(parts.get(2)));
//
//        }
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

    }


    private boolean esCorrectoFecha(String fechatiempo) {
        if (fechatiempo.isEmpty()) {
            fechaLayout.setError("Ingrese una fecha de finalizacion de sala");
            horaLayout.setEnabled(false);
            return false;
        } else {
            horaLayout.setEnabled(true);
            fechaLayout.setError(null);
        }
        return true;
    }

    private boolean esCorrectoHora(String horatiempo) {
        if (horatiempo.isEmpty()) {
            horaLayout.setError("Ingrese una hora de finalizacion de sala");
            return false;
        } else {
            horaLayout.setError(null);
        }
        return true;
    }

    private void validarDatos() {
        String fecha = fechaLayout.getEditText().getText().toString();
        String hora = horaLayout.getEditText().getText().toString();

        boolean a = esCorrectoFecha(fecha);
        boolean b = esCorrectoHora(hora);

        if (a && b) {
            String fechaHora = textTiempoVotacion.getText().toString() + " " + textTiempoHora.getText().toString();
            this.createTiempoDuracion(fechaHora);
        }
    }

    private void createTiempoDuracion(String fechaHora) {
        duracionService.create(getContext(), fechaHora, id);
    }

}
