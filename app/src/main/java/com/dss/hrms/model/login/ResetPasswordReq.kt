package com.dss.hrms.model.login

import com.google.gson.annotations.SerializedName

class ResetPasswordReq {
    @SerializedName("code")
    val code: Int? = null

    @SerializedName("message")
    val message: String? = null

    @SerializedName("status")
    val status: String? = null

    @SerializedName("data")
    val data: Data? = null


    @SerializedName("errors")
    val errors: ArrayList<Error>? = null


    class Data {
        @SerializedName("token")
        val token: String? = null

        @SerializedName("otp_code")
        val otp: String? = null
    }

    class Error {

    }
}