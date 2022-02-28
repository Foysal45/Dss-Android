package com.dss.hrms.view.messaging.model

data class msgResp(
    val code: Int?,
    val message: String?,
    val data: msgData?,
    val status: String?
)