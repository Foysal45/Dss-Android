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
    var uName: String?
        get() = mySharedPref.getString("uName", "")
        set(uName) {
            val editor = mySharedPref.edit()
            editor.putString("uName", uName)
            editor.apply()
        }
    var uEmployeeId: Int?
        get() = mySharedPref.getInt("uEmployeeId", 0)
        set(uEmployeeId) {
            val editor = mySharedPref.edit()
            editor.putInt("uEmployeeId", uEmployeeId!!)
            editor.apply()
        }

    fun getFirstNnme(): String {
        return uName!!.split(" ")[0]
    }

    var uEmail: String?
        get() = mySharedPref.getString("uEmail", "")
        set(uEmail) {
            val editor = mySharedPref.edit()
            editor.putString("uEmail", uEmail)
            editor.apply()
        }

    var uPhone: String?
        get() = mySharedPref.getString("uPhone", "01")
        set(uPhone) {
            val editor = mySharedPref.edit()
            editor.putString("uPhone", uPhone)
            editor.apply()
        }

    var uPassword: String?
        get() = mySharedPref.getString("uPassword", "")
        set(uPassword) {
            val editor = mySharedPref.edit()
            editor.putString("uPassword", uPassword)
            editor.apply()
        }

    var access_token: String?
        get() = mySharedPref.getString("access_token", "")
        set(access_token) {
            val editor = mySharedPref.edit()
            editor.putString("access_token", "Bearer " + access_token)
            editor.apply()
        }


    var isLogin: Boolean?
        get() = mySharedPref.getBoolean("isLogin", false)
        set(isLogin) {
            val editor = mySharedPref.edit()
            editor.putBoolean("isLogin", isLogin!!)
            editor.apply()
        }




    init {
        mySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE)
    }
}
