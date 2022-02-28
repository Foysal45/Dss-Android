package com.dss.hrms.util

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object {
        fun changeDateFormateForShowing(yyyy_mm_dd: String?): String? {
            try {
                val currentFormate = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
                val newDate: Date = currentFormate.parse(yyyy_mm_dd)
                var newFormate = SimpleDateFormat("dd-MM-yyyy", Locale("en"))
                val date: String = newFormate.format(newDate)
                return  date
            }catch (e:Exception){
                return  null
            }
        }

        fun changeDateFormateForSending(dd_mm_yyyy: String?): String? {
            try {
                val currentFormate = SimpleDateFormat("dd-MM-yyyy", Locale("en"))
                val newDate: Date = currentFormate.parse(dd_mm_yyyy)
                var newFormate = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
                val date: String = newFormate.format(newDate)
                return date
            } catch (e: Exception) {
               return null
            }
        }
    }

}