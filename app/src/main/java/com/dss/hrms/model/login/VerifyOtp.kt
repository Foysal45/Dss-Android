package com.dss.hrms.model.login

import com.google.gson.annotations.SerializedName

class VerifyOtp {

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
        @SerializedName("reset_token")
        val reset_token: String? = null
    }

    class Error {

    }
}