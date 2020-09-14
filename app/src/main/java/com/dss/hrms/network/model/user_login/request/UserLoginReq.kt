package com.dss.hrms.network.model.user_login.request

/**
 * UserLoginReq.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 11-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class UserLoginReq(
    private var email: String,
    private var password: String
) {
    fun setEmail(email: String) {
        this.email = email
    }

    fun getEmail(): String {
        if (email.equals(null)) {
            return ""
        }
        return email
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

}