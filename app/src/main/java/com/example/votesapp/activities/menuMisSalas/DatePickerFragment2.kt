package com.example.votesapp.activities.menuMisSalas

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.votesapp.activities.menuMisSalas.DatePickerFragment2
import com.example.votesapp.activities.registro_usuario.DatePickerFragment
import java.util.*

class DatePickerFragment2 : DialogFragment() {
    private var listener: DatePickerDialog.OnDateSetListener? = null
    private var initialYear: Int = -1
    private var initialMonth: Int = -1
    private var initialDay: Int = -1

    fun setListener(listener: DatePickerDialog.OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Current date
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)

        // Initial selected value
        if (initialYear == -1)
            initialYear = year

        if (initialMonth == -1)
            initialMonth = c.get(Calendar.MONTH)

        if (initialDay == -1)
            initialDay = c.get(Calendar.DAY_OF_MONTH)
//        val c = Calendar.getInstance()
//        val year = c[Calendar.YEAR]
//        val month = c[Calendar.MONTH]
//        val day = c[Calendar.DAY_OF_MONTH]
//        val datepickerdialog = DatePickerDialog(
//            requireActivity(), listener, year, month, day
//        )
        val datePickerDialog = DatePickerDialog(requireActivity(),listener, initialYear, initialMonth -1, initialDay)


        //c.set(Calendar.YEAR,year)
        datePickerDialog.datePicker.minDate = c.timeInMillis

        return datePickerDialog
    }

    companion object {
        @JvmStatic
        fun newInstance(listener: DatePickerDialog.OnDateSetListener,year: Int,month: Int,day: Int): DatePickerFragment2 {
            val fragment = DatePickerFragment2()
            fragment.listener = listener
            fragment.initialYear = year
            fragment.initialMonth = month
            fragment.initialDay = day
            fragment.listener = listener
            return fragment
        }
        @JvmStatic
        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragment2 {
            val fragment = DatePickerFragment2()
            fragment.listener= listener
            return fragment
        }

    }
}