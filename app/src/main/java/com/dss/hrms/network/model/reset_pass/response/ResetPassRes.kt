package com.dss.hrms.network.model.reset_pass.response

import com.google.gson.annotations.SerializedName

/**
 * AdditionalProfileQualificationRes.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 20-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class ResetPassRes(
    private var message: String?
) {
    fun setMessage(message: String) {
        this.message = message
    }

    fun getMessage(): String {
        if (message.equals(null)) {
            return ""
        }
        return message!!
    }


}