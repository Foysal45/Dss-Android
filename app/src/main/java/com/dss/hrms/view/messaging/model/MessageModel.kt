package com.dss.hrms.view.messaging.model

import java.io.Serializable

data class MessageModel(
    val created_at: String?,
    val employee_id: List<Int?>?,
    val employees: List<Employee?>?,
    val id: Int?,
    val message_body: String?,
    val office_id: List<Any?>?,
    val offices: List<Any?>?,
    val sent_at: String?,
    val updated_at: String?
) : Serializable {
    data class Employee(
        val blood_group_id: Int?,
        val created_at: String?,
        val date_of_birth: String?,
        val deleted_at: Any?,
        val designation: Designation?,
        val designation_id: Int?,
        val disability_degree_id: Int?,
        val disability_document_path: Any?,
        val disability_type_id: Int?,
        val disabled_person_id: String?,
        val employee_type_id: Int?,
        val fathers_name: String?,
        val fathers_name_bn: String?,
        val freedom_fighter_document_path: Any?,
        val gender_id: Int?,
        val has_disability: Boolean?,
        val has_freedom_fighter_quota: Boolean?,
        val id: Int?,
        val job_joining_date: String?,
        val marital_status_id: Int?,
        val mothers_name: String?,
        val mothers_name_bn: String?,
        val name: String?,
        val name_bn: String?,
        val nid_no: Long?,
        val office: Office?,
        val office_id: Int?,
        val photo: String?,
        val present_basic_salary: Int?,
        val present_gross_salary: Any?,
        val profile_id: String?,
        val punch_id: Any?,
        val religion_id: Int?,
        val status: Int?,
        val tin_no: Any?,
        val updated_at: String?,
        val user: User?
    ) : Serializable {
        data class Designation(
            val created_at: String?,
            val deleted_at: Any?,
            val grade_id: Any?,
            val id: Int?,
            val name: String?,
            val name_bn: String?,
            val priority_order: Int?,
            val status: Int?,
            val updated_at: String?
        ) : Serializable

        data class Office(
            val column_no: Int?,
            val created_at: String?,
            val deleted_at: Any?,
            val district_id: Any?,
            val division_id: Any?,
            val email: Any?,
            val fax: Any?,
            val head_office_department_id: Any?,
            val head_office_section_id: Any?,
            val head_office_sub_section_id: Any?,
            val head_office_sub_sub_section_id: Any?,
            val id: Int?,
            val mobile: Any?,
            val office_address: Any?,
            val office_category_id: Any?,
            val office_code: String?,
            val office_location_id: Int?,
            val office_name: String?,
            val office_name_bn: String?,
            val office_type_id: Int?,
            val phone: Any?,
            val row_no: Int?,
            val sixteen_category_id: Int?,
            val status: Int?,
            val upazila_id: Any?,
            val updated_at: String?
        ) : Serializable

        data class User(
            val created_at: String?,
            val deleted_at: Any?,
            val email: String?,
            val email_verified_at: Any?,
            val employee_id: Int?,
            val id: Int?,
            val phone_number: String?,
            val status: Int?,
            val updated_at: String?,
            val username: String?
        ) : Serializable
    }
}