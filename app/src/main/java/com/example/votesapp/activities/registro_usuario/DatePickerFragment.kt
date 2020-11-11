package com.example.votesapp.activities.registro_usuario

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {
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

        val datePickerDialog = DatePickerDialog(requireActivity(),AlertDialog.THEME_HOLO_LIGHT ,listener, initialYear, initialMonth -1, initialDay)

        // Min and max date
        c.set(Calendar.YEAR, year - 110)
        datePickerDialog.datePicker.minDate = c.timeInMillis
        c.set(Calendar.YEAR, year - 13)
        datePickerDialog.datePicker.maxDate = c.timeInMillis

        return datePickerDialog
    }

    fun onDateSet(datePicker: DatePicker?, i: Int, i1: Int, i2: Int) {}

    companion object {
        fun newInstance(listener: DatePickerDialog.OnDateSetListener,year: Int,month: Int,day: Int): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.listener = listener
            fragment.initialYear = year
            fragment.initialMonth = month
            fragment.initialDay = day
            fragment.listener = listener
            return fragment
        }

        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.listener = listener
            return fragment


        }
    }

}