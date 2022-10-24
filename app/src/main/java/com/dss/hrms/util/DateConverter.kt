package com.dss.hrms.util

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object {
        fun changeDateFormateForShowing(yyyy_mm_dd: String?): String? {
            try {
                val currentFormate = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
                val newDate: Date = yyyy_mm_dd?.let { currentFormate.parse(it) } as Date
                val newFormate = SimpleDateFormat("dd-MM-yyyy", Locale("en"))
                return newFormate.format(newDate)
            } catch (e: Exception) {
                return null
            }
        }

        fun changeDateFormateForSending(dd_mm_yyyy: String?): String? {
            try {
                val currentFormate = SimpleDateFormat("dd-MM-yyyy", Locale("en"))
                val newDate: Date = dd_mm_yyyy?.let { currentFormate.parse(it) } as Date
                val newFormate = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
                return newFormate.format(newDate)
            } catch (e: Exception) {
                return null
            }
        }
    }

}