package com.dss.hrms.model.login

import com.google.gson.annotations.SerializedName

 data class LoginResponse(
    @SerializedName("code") val code: Int?,
    @SerializedName("status")  val status: String?,
    @SerializedName("message")  val message: String?,
    @SerializedName("data")  val data: LoginInfo?
)