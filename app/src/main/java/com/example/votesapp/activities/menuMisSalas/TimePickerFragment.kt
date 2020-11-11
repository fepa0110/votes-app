package com.example.votesapp.activities.menuMisSalas

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment(){
    private var listener: TimePickerDialog.OnTimeSetListener?= null

    fun setListener(listener: TimePickerDialog.OnTimeSetListener?){
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]

        val timePickerDialog = TimePickerDialog(
            requireActivity(),
            AlertDialog.THEME_HOLO_LIGHT,
            listener,
            hour,
            minute,
            false
        )


        // Create a new instance of TimePickerDialog and return it

        return timePickerDialog

    }

    companion object{
        @JvmStatic
        fun newInstance(
            listener: TimePickerDialog.OnTimeSetListener?
        ) : TimePickerFragment {
            val fragment = TimePickerFragment()
            fragment.setListener(listener)
            return  fragment
        }
    }
}