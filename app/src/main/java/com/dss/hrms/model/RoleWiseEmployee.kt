package com.dss.hrms.model

import android.os.Parcelable
import com.dss.hrms.model.employeeProfile.Employee
import com.dss.hrms.view.training.model.BudgetAndSchedule
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class RoleWiseEmployeeResponseClass {
    data class RoleWiseEmployeeRes(
        val status: String?,
        val code: Int?,
        val data: List<RoleWiseEmployee>?
    )

    @Parcelize
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
        val disabled_person_id: String?,
        val office_id: Int?,
        val designation_id: Int?,
        val job_joining_date: String?,
        val employee_type_id: Int?,
        val status: Int?,
        val office: Office?,
        val designation: Designation?
    ) : Parcelable

    @Parcelize
    class Office : Parcelable {
        @SerializedName("id")
        val id: Int? = null

        @SerializedName("office_type_id")
        val office_type_id: Int? = 0

        @SerializedName("office_name")
        val name: String? = null

        @SerializedName("office_name_bn")
        val name_bn: String? = null

        @SerializedName("status")
        val status: Int = 0

        @SerializedName("deleted_at")
        val deleted_at: String? = null

        @SerializedName("created_at")
        val created_at: String? = null

        @SerializedName("updated_at")
        val updated_at: String? = null
    }

    @Parcelize
    data class Designation(
        val id: Int?,
        val name: String?,
        val name_bn: String?,
        val priority_order: Int?,
        val status: Int?
    ) : Parcelable

    data class EmployeeListResponse(
        val status: String?,
        val code: Int?,
        val data: EmployeeListData?
    )

    data class EmployeeListData(
        val data: List<RoleWiseEmployee>?
    )
}