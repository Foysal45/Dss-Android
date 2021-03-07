package com.dss.hrms.model

import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.view.training.model.BudgetAndSchedule

class RoleWiseEmployeeResponseClass {
    data class RoleWiseEmployeeRes(
        val status: String?,
        val code: Int?,
        val data: List<RoleWiseEmployee>?
    )

    data class RoleWiseEmployee(
        val id: Int?,
        val profile_id: String?,
        val name: String?,
        val name_bn: String?,
        val photo: String?,
        val date_of_birth: String?,
        val gender_id: Int?,
        val fathers_name: String?,
        val fathers_name_bn: String?,
        val mothers_name: String?,
        val mothers_name_bn: String?,
        val nid_no: String?,
        val punch_id: String?,
        val religion_id: Int?,
        val blood_group_id: Int?,
        val marital_status_id: Int?,
        val has_disability: Int?,
        val has_freedom_fighter_quota: Int?,
        val disability_type_id: Int?,
        val disability_degree_id: Int?,
        val disabled_person_id: Int?,
        val office_id: Int?,
        val designation_id: Int?,
        val job_joining_date: String?,
        val employee_type_id: Int?,
        val status: Int?,
        val office: Office?,
        val designation: BudgetAndSchedule.Designation?
    )
}