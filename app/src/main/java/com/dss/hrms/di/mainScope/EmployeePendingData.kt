package com.dss.hrms.di.mainScope

import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.model.pendingDataModel.PendingDataModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EmployeePendingData @Inject constructor() {
    var PendingData: PendingDataModel? = null
}