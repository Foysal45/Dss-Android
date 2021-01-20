package com.dss.hrms.di.mainScope

import com.dss.hrms.model.employeeProfile.Employee
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EmployeeProfileData @Inject constructor(){
    var employee: Employee? = null
}