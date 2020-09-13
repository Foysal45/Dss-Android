package com.dss.hrms.activity.ac_base.view

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dss.hrms.R
import java.lang.reflect.Method


open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        StatusBarLightMode(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

    }

    fun StatusBarLightMode(activity: Activity): Int {
        var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity, true)) {
                result = 1
            } else if (FlymeSetStatusBarLightMode(activity.window, true)) {
                result = 2
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                result = 3
            }
        }
        return result
    }

    fun FlymeSetStatusBarLightMode(
        window: Window?,
        dark: Boolean
    ): Boolean {
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
                darkFlag.setAccessible(true)
                meizuFlags.setAccessible(true)
                val bit: Int = darkFlag.getInt(null)
                var value: Int = meizuFlags.getInt(lp)
                value = if (dark) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {
            }
        }
        return result
    }

    fun MIUISetStatusBarLightMode(activity: Activity, dark: Boolean): Boolean {
        var result = false
        val window = activity.window
        if (window != null) {
            val clazz: Class<*> = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams =
                    Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField: Method = clazz.getMethod(
                    "setExtraFlags",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag)
                }
                result = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (dark) {
                        activity.window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }
    fun toast(context: Context, massage: String) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }
}