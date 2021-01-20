package com.dss.hrms.util

import android.app.DatePickerDialog
import android.content.Context
import com.dss.hrms.view.allInterface.OnDateListener
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
                    onDatelistener.onDate("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                }, year, month, day
            )
        }
        dpd?.show()
    }
}