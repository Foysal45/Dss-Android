package com.chaadride.shared_pref

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    var mySharedPref: SharedPreferences

    var lang: Int?
        get() = mySharedPref.getInt("lang", 1)
        set(lang) {
            val editor = mySharedPref.edit()
            editor.putInt("lang", lang!!)
            editor.apply()
        }
    var isEnglish: Boolean?
        get() = mySharedPref.getBoolean("isEnglish", true)
        set(isEnglish) {
            val editor = mySharedPref.edit()
            editor.putBoolean("isEnglish", isEnglish!!)
            editor.apply()
        }


    init {
        mySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE)
    }
}
