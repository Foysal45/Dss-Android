package com.dss.hrms.network.model.user_login.response

import com.google.gson.annotations.SerializedName

/**
 * UserLoginReq.kt
 * DSS-HRMS
 * Crated by Towhidur Rahman on 11-Sep-20
 * Copyright © 2020 SIMEC System LTD. All rights reserved.
 */
class Data(
    @SerializedName("employee_id")
    private var employeeId: Int?,
    @SerializedName("phone_number")
    private var phoneNumber: String?,
    private var username: String?,
    private var email: String?,
    private var token: String?
) {
    fun setEmployeeId(employeeId: Int) {
        this.employeeId = employeeId
    }

    fun getEmployeeId(): Int {
        if (employeeId==null) {
            return 0
        }
        return employeeId!!
    }

    fun setPhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    fun getPhoneNumber(): String {
        if (phoneNumber.equals(null)) {
            return ""
        }
        return phoneNumber!!
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getUsername(): String {
        if (username.equals(null)) {
            return ""
        }
        return username!!
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getEmail(): String {
        if (email.equals(null)) {
            return ""
        }
        return email!!
    }
    fun setToken(token: String) {
        this.token = token
    }
    fun getToken(): String {
        if (token.equals(null)) {
            return ""
        }
        return token!!
    }
}