package com.dss.hrms.model.login

import com.google.gson.annotations.SerializedName

class ResetPassword {
    @SerializedName("code")
    val code: Int? = null

    @SerializedName("message")
    val message: String? = null

    @SerializedName("status")
    val status: String? = null

    @SerializedName("data")
    val data: ArrayList<Data>? = null


    @SerializedName("errors")
    val errors: ArrayList<Error>? = null


    class Data {

    }

    class Error {

    }
}