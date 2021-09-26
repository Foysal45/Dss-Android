package com.dss.hrms.model.pendingDataModel

data class PendingDataResponse(
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: PendingDataModel?
)