package com.dss.hrms.network.model.forget_pass.response

import com.google.gson.annotations.SerializedName

/**
 * Data.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 20-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class Data(
    @SerializedName("token")
    private var token: String?,
    @SerializedName("otp_code")
    private var otp_code: String?
) {

    fun setToken(token: String) {
        this.token = token
    }

    fun getToken(): String {
        if (token.equals(null)) {
            return ""
        }
        return token!!
    }

    fun setOtp_code(otp_code: String) {
        this.otp_code = otp_code
    }

    fun getOtp_code(): String {
        if (otp_code.equals(null)) {
            return ""
        }
        return otp_code!!
    }





}