package com.dss.hrms.model.login

data class LoginResponse(
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: LoginInfo?
)