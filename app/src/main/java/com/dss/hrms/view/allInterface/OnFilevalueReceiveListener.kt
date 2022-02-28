package com.dss.hrms.view.allInterface

import android.graphics.Bitmap
import java.io.File

interface OnFilevalueReceiveListener {
    fun onFileValue(imageFile: File, bitmap: Bitmap?)
}