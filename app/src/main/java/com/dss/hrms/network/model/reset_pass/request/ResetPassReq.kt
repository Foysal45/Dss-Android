package com.dss.hrms.network.model.reset_pass.request

/**
 * UserLoginReq.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 11-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class ResetPassReq(
    private var reset_token: String,
    private var password: String,
    private var password_confirmation: String
) {
    fun setReset_token(reset_token: String) {
        this.reset_token = reset_token
    }

    fun getReset_token(): String {
        if (reset_token.equals(null)) {
            return ""
        }
        return reset_token
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getPassword(): String {
        if (password.equals(null)) {
            return ""
        }
        return password
    }

    fun setPassword_confirmation(password_confirmation: String) {
        this.password_confirmation = password_confirmation
    }

    fun getPassword_confirmation(): String {
        if (password_confirmation.equals(null)) {
            return ""
        }
        return password_confirmation
    }
}