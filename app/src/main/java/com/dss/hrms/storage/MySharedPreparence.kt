package com.namaztime.namaztime.database

import android.content.Context
import android.content.SharedPreferences

class MySharedPreparence {

    private var context: Context? = null
    var preferences: SharedPreferences? = null
    private val SHARED_PREFERENCE_NAME = "mysharedprefarence"
    private val TOKEN = "token"
    private val LANGUAGE = "language"
    private val LOGIN_STATUS = "loginstatus"
    private val REMEMBER = "remember"
    private val EMAIL = "email"
    private val PASSWORD = "password"
    private val LOGIN_INFO = "logininfo"


    constructor(context: Context) {
        preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        this.context = context
    }

    fun setToken(value: String) {
        val editor = preferences!!.edit()
        editor.putString(TOKEN, value)
        editor.commit()
    }

    fun getToken(): String? {
        return preferences!!.getString(TOKEN, null);
    }

    fun setLanguage(value: String?) {
        val editor = preferences!!.edit()
        editor.putString(LANGUAGE, value)
        editor.commit()
    }

    fun getLanguage(): String? {
        return preferences!!.getString(LANGUAGE, "bn");
    }

    fun setLoginStatus(value: Boolean) {
        val editor = preferences!!.edit()
        editor.putBoolean(LOGIN_STATUS, value)
        editor.commit()
    }

    fun isLogin(): Boolean? {
        return preferences!!.getBoolean(LOGIN_STATUS, false);
    }

    fun setRemember(value: Boolean) {
        val editor = preferences!!.edit()
        editor.putBoolean(REMEMBER, value)
        editor.commit()
    }

    fun isRemember(): Boolean? {
        return preferences!!.getBoolean(REMEMBER, false);
    }

    fun setEmail(value: String) {
        val editor = preferences!!.edit()
        editor.putString(EMAIL, value)
        editor.commit()
    }

    fun getEmail(): String? {
        return preferences!!.getString(EMAIL, null);
    }

    fun setPassword(value: String) {
        val editor = preferences!!.edit()
        editor.putString(PASSWORD, value)
        editor.commit()
    }

    fun getPassword(): String? {
        return preferences!!.getString(PASSWORD, null);
    }

    fun setLoginInfo(value: String) {
        val editor = preferences!!.edit()
        editor.putString(LOGIN_INFO, value)
        editor.commit()
    }

    fun getLoginInfo(): String? {
        return preferences!!.getString(LOGIN_INFO, null);
    }

}
