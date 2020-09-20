package com.dss.hrms.network.model.additional_profile_qualification.response

import com.google.gson.annotations.SerializedName

/**
 * AdditionalProfileQualificationRes.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 20-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class AdditionalProfileQualificationRes(
    private var message: String?,
    @SerializedName("data")
    private var data: List<Data>
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

    fun setData(data: List<Data>) {
        this.data = data
    }

    fun getData(): List<Data> {
        return data
    }

}