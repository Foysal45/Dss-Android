package com.dss.hrms.network.model.otp.request

/**
 * UserLoginReq.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 11-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class OtpReq(
    private var token: String,
    private var otp_code: String
) {
    fun setToken(token: String) {
        this.token = token
    }

    fun getToken(): String {
        if (token.equals(null)) {
            return ""
        }
        return token
    }

    fun setOtp_code(otp_code: String) {
        this.otp_code = otp_code
    }

    fun getOtp_code(): String {
        if (otp_code.equals(null)) {
            return ""
        }
        return otp_code
    }
}