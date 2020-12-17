package com.dss.hrms.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*

/**
 * LanguageChange.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 10-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
public class LanguageChange {

    companion object{
        var mySharedPref:SharedPreferences? = null
        fun languageSet(context: Context){
            mySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE)

            setLocal(
                context,
                langA!!
            )
        }
        private fun setLocal(context: Context, lang: String) {
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            context.resources.updateConfiguration(
                config,
                context.resources.displayMetrics
            )
        }

        var langA: String?
            get() = mySharedPref!!.getString("langA", "en")
          //  get() ="en"
            set(langA) {
                val editor = mySharedPref!!.edit()
                editor.putString("langA", langA!!)
                editor.apply()
            }
    }



}