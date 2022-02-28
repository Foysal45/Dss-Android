package com.dss.hrms.util

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.dss.hrms.view.allInterface.OnDateListener
import java.text.SimpleDateFormat
import java.util.*


class DatePicker {
    fun showDatePicker(context: Context?, onDatelistener: OnDateListener) {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = context?.let {
            DatePickerDialog(
                it,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    //  onDatelistener.onDate("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                    onDatelistener.onDate("" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year)
                }, year, month, day
            )
        }
        dpd?.show()
    }
}