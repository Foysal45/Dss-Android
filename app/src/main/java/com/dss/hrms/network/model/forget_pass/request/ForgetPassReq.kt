package com.dss.hrms.network.model.forget_pass.request

/**
 * UserLoginReq.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 11-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class ForgetPassReq(
    private var phone_number: String
) {
    fun setPhone_number(email: String) {
        this.phone_number = phone_number
    }

    fun getPhone_number(): String {
        if (phone_number.equals(null)) {
            return ""
        }
        return phone_number
    }
}