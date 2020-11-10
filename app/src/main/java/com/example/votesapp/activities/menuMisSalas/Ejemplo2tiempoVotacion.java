package com.example.votesapp.activities.menuMisSalas;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.votesapp.R;

import java.util.Calendar;

public class Ejemplo2tiempoVotacion extends Fragment {

    TextView TxtFecha, TxtHoras;
    Button BtnFecha,BtnHoras;
    DatePicker DatePicker;
    TimePicker TimePicker;
    int Año, Mes, Dia, Hora, Min, Seg;
    String Horario = " a.m.";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ejemplo2_tiempo_votacion,container,false);

        TxtFecha = (TextView) view.findViewById(R.id.TxtFecha);
        TxtHoras = (TextView) view.findViewById(R.id.TxtHoras);
        BtnFecha = (Button) view.findViewById(R.id.BtnFecha);
        BtnFecha.setOnClickListener(new ClickEvent());
        BtnHoras = (Button) view.findViewById(R.id.BtnHoras);
        BtnHoras.setOnClickListener(new ClickEvent());

        Calendar Calendario = Calendar.getInstance();
        Dia = Calendario.get(Calendar.DAY_OF_MONTH);
        Mes = Calendario.get(Calendar.MONTH);
        Año = Calendario.get(Calendar.YEAR);
        Hora = Calendario.get(Calendar.HOUR);
        Min = Calendario.get(Calendar.MINUTE);
        Seg = Calendario.get(Calendar.SECOND);

        if(Calendario.get(Calendar.HOUR_OF_DAY)>12) Horario = " p.m.";
        else Horario = " a.m.";

        CambiarFecha(Año,Mes+1,Dia);
        CambiarHoras(Hora,Min,Seg,Horario);

        DatePicker = (DatePicker) view.findViewById(R.id.Calendario);
        DatePicker.init(Año, Mes, Dia, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                CambiarFecha(year,month+1,day);
            }
        });

        TimePicker = (TimePicker) view.findViewById(R.id.Horas);
        TimePicker.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener(){
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(hourOfDay>12) Horario = " p.m.";
                else Horario = " a.m.";
                CambiarHoras(hourOfDay,minute,Seg,Horario);
            }});
        return view;
    }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View Btn) {
            if (Btn == BtnFecha) {
                ObtenerFecha();
            }
            if(Btn == BtnHoras){
                ObtenerHora();
            }
        }
    }

    private  void CambiarFecha(int Año, int Mes, int Dia){
        String CeroD = "", CeroM = "";
        if(Dia<10) CeroD = "0";
        if(Mes<10) CeroM = "0";
        TxtFecha.setText(CeroD + Dia + " / " + CeroM + Mes + " / " + Año);
    }

    private void CambiarHoras(int Hora, int Min, int Seg, String Horario){
        String CeroH = "", CeroM = "", CeroS="";
        if(Hora<10) CeroH = "0";
        if(Min<10) CeroM = "0";
        if(Seg<10) CeroS = "0";
        TxtHoras.setText(CeroH + Hora + " : " + CeroM + Min + " : " + CeroS + Seg + Horario);
    }

    private void ObtenerFecha(){
        DatePickerDialog SeleccionarFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                CambiarFecha(year,month+1,dayOfMonth);
            }
        },Año, Mes, Dia);
        SeleccionarFecha.show();
    }

    private void ObtenerHora(){
        TimePickerDialog SeleccionarHora = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if(hourOfDay>12) Horario = " p.m.";
                else Horario = " a.m.";
                CambiarHoras(hourOfDay,minute,Seg, Horario);
            }
        }, Hora, Min, false);
        SeleccionarHora.show();
    }

}
