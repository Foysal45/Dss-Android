package com.dss.hrms.util

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object {
        fun changeDateFormateForShowing(yyyy_mm_dd: String): String {
            val currentFormate = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
            val newDate: Date = currentFormate.parse(yyyy_mm_dd)
            var newFormate = SimpleDateFormat("dd-MM-yyyy", Locale("en"))
            val date: String = newFormate.format(newDate)
            return date
        }

        fun changeDateFormateForSending(dd_mm_yyyy: String): String {
            val currentFormate = SimpleDateFormat("dd-MM-yyyy", Locale("en"))
            val newDate: Date = currentFormate.parse(dd_mm_yyyy)
            var newFormate = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
            val date: String = newFormate.format(newDate)
            return date
        }
    }

}