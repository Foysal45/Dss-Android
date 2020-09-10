package com.dss.hrms.Helper

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import java.util.*

/**
 * LocalHelper.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 10-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
public object LocalHelper {
    private const val SELECTED_LANGUAGE = "Locale.com.dss.hrms.Helper.Selected.Language"

    fun onAttach(context: Context?): Context? {
        val lang: String =
            this.getParsistedData(
                context!!,
                Locale.getDefault().language
            )!!
        return setLocal(context, lang)
    }


    fun onAttach(
        context: Context?,
        defaultLanguage: String?
    ): Context? {
        val lang: String = this.getParsistedData(
            context!!,
            defaultLanguage!!
        )!!
        return setLocal(context, lang)
    }

    private fun setLocal(
        context: Context,
        lang: String
    ): Context? {
        persist(context, lang)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) updateRecources(
            context,
            lang
        ) else updateResourcesLegacy(
            context,
            lang
        )
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateRecources(
        context: Context,
        lang: String
    ): Context? {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return context.createConfigurationContext(config)
    }

    private fun updateResourcesLegacy(
        context: Context,
        lang: String
    ): Context? {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val resources = context.resources
        val config = resources.configuration
        config.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) config.setLayoutDirection(
            locale
        )
        resources.updateConfiguration(config, resources.displayMetrics)
        return context
    }
    private fun persist(context: Context, lang: String) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString(SELECTED_LANGUAGE, lang)
        editor.apply()
    }

    private fun getParsistedData(
        context: Context,
        language: String
    ): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, language)
    }
}