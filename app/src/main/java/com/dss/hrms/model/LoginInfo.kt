package com.dss.hrms.model

import com.google.gson.annotations.SerializedName

class LoginInfo {

    @SerializedName("id")
    val id: Int? = null

    @SerializedName("employee_id")
    val employee_id: Int? = null

    @SerializedName("phone_number")
    val phone_number: Int? = null

    @SerializedName("username")
    val username: String? = null

    @SerializedName("email")
    val email: String? = null

    @SerializedName("email_verified_at")
    val email_verified_at: String? = null

    @SerializedName("status")
    val status: Int? = null

    @SerializedName("deleted_at")
    val deleted_at: String? = null

    @SerializedName("created_at")
    val created_at: String? = null

    @SerializedName("updated_at")
    val updated_at: String? = null

    @SerializedName("token")
    val token: String? = null

    var password: String? = null

}