package com.dss.hrms.network.model.otp.reponse

import com.google.gson.annotations.SerializedName

/**
 * Data.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 20-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class Data(
    @SerializedName("reset_token")
    private var reset_token: String?
) {

    fun setReset_token(reset_token: String) {
        this.reset_token = reset_token
    }

    fun getReset_token(): String {
        if (reset_token.equals(null)) {
            return ""
        }
        return reset_token!!
    }






}