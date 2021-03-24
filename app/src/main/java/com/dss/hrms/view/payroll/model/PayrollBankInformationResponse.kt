package com.dss.hrms.view.payroll.model

import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.view.training.model.BudgetAndSchedule

data class PayrollBankInformationResponse(
    val status: String,
    val message: String,
    val code: Int,
    val data: List<PayRollBankInfo?>
)

data class PayRollBankInfo(
    val id: Int?,
    val employee_id: Int?,
    val bank_name: String?,
    val branch_name: String?,
    val district_id: Int?,
    val account_no: String?,
    val account_name: String?,
    val routing_no: String?,
    val account_type_id: String?,
    val employee: RoleWiseEmployeeResponseClass.RoleWiseEmployee?,
    val district: Employee.District?,
    val account_type: Employee.District?
)