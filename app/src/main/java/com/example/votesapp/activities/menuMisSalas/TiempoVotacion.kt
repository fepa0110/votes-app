package com.example.votesapp.activities.menuMisSalas

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.votesapp.R
import com.example.votesapp.activities.menuMisSalas.TimePickerFragment.Companion.newInstance
import com.example.votesapp.activities.registro_usuario.DatePickerFragment
import com.example.votesapp.services.DuracionService
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_registro_usuario2.*
import java.util.*

class TiempoVotacion : Fragment() {
    private var salaId : Int? = null
    private var guardarDuracion: Button? = null
    private var fechaLayout: TextInputLayout? = null
    private var horaLayout: TextInputLayout? = null
    private var textTiempoVotacion: TextInputEditText? = null
    private var textTiempoHora: TextInputEditText? = null
    var duracionService = DuracionService()
    var getDate = GregorianCalendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_tiempo_votacion, container, false)
        salaId = this.activity?.intent?.getIntExtra("param_id",0)

//    if(!textTiempoHora.toString().equals("null")){
//        textTiempoVotacion.setText(textTiempoVotacion.getEditableText());
//        textTiempoHora.setText(textTiempoHora.getEditableText());
//        fechaLayout.setVisibility(View.INVISIBLE);
//        horaLayout.setVisibility(View.INVISIBLE);
//        guardarDuracion.setVisibility(View.INVISIBLE);
//
//    }
        fechaLayout = view.findViewById<View>(R.id.fecha) as TextInputLayout
        horaLayout = view.findViewById<View>(R.id.hora) as TextInputLayout
        guardarDuracion = view.findViewById<View>(R.id.boton_guardar_tiempo_votacion) as Button
        textTiempoVotacion = view.findViewById<View>(R.id.text_tiempo_votacion) as TextInputEditText
        textTiempoHora = view.findViewById<View>(R.id.text_tiempo_hora) as TextInputEditText
        horaLayout!!.isEnabled = false
        //        if (!textTiempoVotacion.getText().toString().equals(null)){
//            horaLayout.setEnabled(false);
//        }
        textTiempoVotacion!!.setOnClickListener { showDatePickerDialog() }
        textTiempoHora!!.setOnClickListener { showTimePickerDialog() }
        guardarDuracion!!.setOnClickListener { validarDatos() }
        textTiempoVotacion!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                esCorrectoFecha(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        textTiempoHora!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                esCorrectoHora(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        return view
    }

    private fun showTimePickerDialog() {
        val newFragment = newInstance { timePicker, hour, minute ->
            val selectedTime: String
            
            Log.i("fecha hoy", textTiempoVotacion!!.text.toString())
            val fechaActual = getDate[Calendar.YEAR].toString() + "-" + (getDate[Calendar.MONTH] + 1) + "-" + getDate[Calendar.DAY_OF_MONTH]

            val horasActual = getDate[Calendar.HOUR].toString() + ":" + getDate[Calendar.MINUTE] + " " +getDate[Calendar.AM_PM]
            Log.i("fechaActual", fechaActual)

            var horaActual = getDate[Calendar.HOUR]
            var minutosActuales = getDate[Calendar.MINUTE]

            if (fechaActual.equals( textTiempoVotacion!!.text.toString())) {
                if(getDate[Calendar.AM_PM]==Calendar.PM) {
                    horaActual = horaActual + 12
                }
                Log.i("Hora Actual Calendar",horaActual.toString())
                if (hour > horaActual && minute >minutosActuales || hour > horaActual && minute == minutosActuales || hour== horaActual && minute>minutosActuales) {

                        selectedTime = "$hour:$minute"
                        textTiempoHora!!.setText(selectedTime)
                    Log.i("Hora",hour.toString()+":"+minute.toString());
                    Log.i("calendar Hora", getDate[Calendar.HOUR].toString() + ":" + getDate[Calendar.MINUTE].toString()+" "+getDate[Calendar.AM_PM].toString());

                } else {
                    textTiempoHora!!.setText(null)
                    horaLayout!!.error = "La hora debe ser mayor al actual"
                    Log.i("errorHora",hour.toString()+":"+minute.toString());
                    Log.i("calendar Hora", getDate[Calendar.HOUR].toString() + ":" + getDate[Calendar.MINUTE].toString()+" "+getDate[Calendar.AM_PM].toString());
                }
            } else {
                selectedTime = "$hour:$minute"
                textTiempoHora!!.setText(selectedTime)
                Log.i("Hora",hour.toString()+":"+minute.toString());
                Log.i("calendar Hora", getDate[Calendar.HOUR].toString() + ":" + getDate[Calendar.MINUTE].toString()+" "+getDate[Calendar.AM_PM].toString());

            }

        }
        newFragment.show(requireActivity().supportFragmentManager, "timePicker")
    }

    private fun showDatePickerDialog() {
        val onNewDateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val dayStr = day
            val monthStr = (month + 1) // +1 because January is zero

            val selectedDate = "$year-$monthStr-$dayStr"
            textTiempoVotacion!!.setText(selectedDate)
        }

        val birthDate = textTiempoVotacion!!.text.toString()

        val newFragment = if (birthDate.isEmpty())
            DatePickerFragment2.newInstance(onNewDateListener)
        else {
            val parts = birthDate.split('-')
            DatePickerFragment2.newInstance(
                onNewDateListener,
                parts[0].toInt(), parts[1].toInt(), parts[2].toInt()
            )
        }

        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun esCorrectoFecha(fechatiempo: String): Boolean {
        if (fechatiempo.isEmpty()) {
            fechaLayout!!.error = "Ingrese una fecha de finalizacion de sala"
            horaLayout!!.isEnabled = false
            return false
        } else {
            horaLayout!!.isEnabled = true
            fechaLayout!!.error = null
        }
        return true
    }

    private fun esCorrectoHora(horatiempo: String): Boolean {
        if (horatiempo.isEmpty()) {
            horaLayout!!.error = "Ingrese una hora de finalizacion de sala"
            return false
        } else {
            horaLayout!!.error = null
        }
        return true
    }

    private fun validarDatos() {
        val fecha = fechaLayout!!.editText!!.text.toString()
        val hora = horaLayout!!.editText!!.text.toString()
        val a = esCorrectoFecha(fecha)
        val b = esCorrectoHora(hora)
        if (a && b) {
            val fecha =textTiempoVotacion!!.text.toString()
            val hora =  textTiempoHora!!.text.toString()
            createTiempoDuracion(fecha,hora)
        }
    }

    private fun createTiempoDuracion(fecha: String,hora: String) {
        salaId?.let { duracionService.create(requireContext(), fecha,hora, it) }
    }


}