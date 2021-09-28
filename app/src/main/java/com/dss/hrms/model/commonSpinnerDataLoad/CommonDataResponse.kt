package com.dss.hrms.model.commonSpinnerDataLoad

import com.dss.hrms.model.pendingDataModel.PendingDataModel

class CommonDataResponse (
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: CommonData?
)