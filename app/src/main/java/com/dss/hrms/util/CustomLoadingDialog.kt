package com.dss.hrms.util

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager.BadTokenException
import com.dss.hrms.R
import java.util.*

class CustomLoadingDialog {

    fun createLoadingDialog(context: Context?): Dialog? {
        val dialog: Dialog = ProgressDialog(context)
        try {
            dialog.show()
        } catch (ignored: BadTokenException) {
        }
        dialog.setCancelable(false)
        Objects.requireNonNull(dialog.window)!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_progress)
        return dialog
    }

}