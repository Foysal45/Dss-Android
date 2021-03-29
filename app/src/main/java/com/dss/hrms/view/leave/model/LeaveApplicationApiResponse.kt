package com.dss.hrms.view.leave.model

import android.os.Parcelable
import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import kotlinx.android.parcel.Parcelize

class LeaveApplicationApiResponse {

    data class LeaveApplicationResponse(
        val status: String?,
        val code: Int?,
        val message: String?,
        val data: List<LeaveApplication>
    )


    @Parcelize
    data class LeaveApplication(
        val id: Int?,
        val office_id: Int?,
        val leave_request_ref: String?,
        val leave_policy_id: Int?,
        val approval_status: String?,
        val apply_date: String?,
        val document_path: String?,
        val status: Int?,
        val leave_policy: LeavePolicy?,
        val leave_application_details: List<LeaveApplicationDetails>?,
        val leave_application_note: List<LeaveApplicationNote>?
    ) : Parcelable

    @Parcelize
    data class LeaveApplicationDetails(
        val id: Int?,
        val leave_application_id: Int?,
        val employee_id: Int?,
        val designation_id: Int?,
        val joining_date: String?,
        val reason: String?,
        val date_form: String?,
        val date_to: String?,
        val responsible_person_id: String?,
        val last_leave_date: String?,
        val present_salary: String?,
        val emergency_contact_no: String?,
        val requested_money: String?,
        val comments: String?,
        val responsible_person: RoleWiseEmployeeResponseClass.RoleWiseEmployee?
    ) : Parcelable

    @Parcelize
    data class LeavePolicyResponse(
        val status: String?,
        val message: String?,
        val code: Int?,
        val data: List<LeaveApplicationApiResponse.LeavePolicy>?
    ) : Parcelable

    @Parcelize
    data class LeavePolicy(
        val id: Int?,
        val leave_name: String?,
        val leave_name_bn: String?,
        val leave_balance: Int?,
        val leave_category: Int?,
        val status: Int?
    ) : Parcelable

    @Parcelize
    data class LeaveApplicationNote(
        val id: Int?,
        val leave_application_id: Int?,
        val forword_by_employee_id: Int?,
        val forword_to_employee_id: Int?,
        val note_leave: String?,
        val note_date: String?,
        val forword_to_employee: RoleWiseEmployeeResponseClass.RoleWiseEmployee?
    ) : Parcelable
}