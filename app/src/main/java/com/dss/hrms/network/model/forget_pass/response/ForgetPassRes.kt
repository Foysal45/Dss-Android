package com.dss.hrms.network.model.forget_pass.response

import com.google.gson.annotations.SerializedName

/**
 * AdditionalProfileQualificationRes.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 20-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class ForgetPassRes(
    private var message: String?,
    @SerializedName("data")
    private var data:  Data
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

    fun setData(data: Data) {
        this.data = data
    }

    fun getData(): Data {
        return data
    }

}