package com.dss.hrms.model.employeeProfile

data class EmployeeResponse(
    val code: Int?,
    val status: String?,
    val message: String?,
    val data: Employee?
)