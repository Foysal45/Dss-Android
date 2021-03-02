package com.dss.hrms.view.training.model

class BudgetAndSchedule {

    data class BatchScheduleResponse(
        val status: String?,
        val code: Int?,
        val data: BatchScheduleData?
    )

    data class BatchScheduleData(val data: List<BatchSchedule>?)
    data class BatchSchedule(
        val id: Int?,
        val course_schedule_id: Int?,
        val batch_name: String?,
        val batch_name_bn: String?,
        val start_date: String?,
        val end_date: String?,
        val reg_start_date: String,
        val reg_end_date: String?,
        val course_coordinator: Int?,
        val staff1: Int?,
        val staff2: Int?,
        val staff3: Int?,
        val status: Int,
        val course_schedule: CourseScheduleBatch
    )

    data class CourseScheduleBatch(
        val id: Int?,
        val course_title: String?,
        val course_title_bn: String?,
        val course_id: Int?,
        val total_seat: Int?,
        val coordinator: Int?,
        val co_coordinator: Int?,
        val staff1: Int?,
        val staff2: Int?,
        val staff3: Int?,
        val status: Int?

    )


    data class CourseScheduleResponse(
        val status: String?,
        val code: Int?,
        val data: CourseScheduleData?
    )

    data class CourseScheduleData(val data: List<CourseSchedule>?)

    data class CourseSchedule(
        val id: Int?,
        val course_title: String?,
        val course_title_bn: String?,
        val course_id: Int?,
        val total_seat: Int?,
        val status: Int?,
        val coordinator: CommonClass?,
        val co_coordinator: CommonClass?,
        val staff1: CommonClass?,
        val staff2: CommonClass?,
        val staff3: CommonClass?
    )

    data class CommonClass(
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
        val tin_no: String?,
        val punch_id: String?,
        val religion_id: Int?,
        val blood_group_id: Int?,
        val marital_status_id: Int?,
        val has_disability: Int?,
        val has_freedom_fighter_quota: Int?,
        val disability_type_id: String?,
        val disability_degree_id: String?,
        val disabled_person_id: String?,
        val office_id: Int?,
        val designation_id: Int?,
        val job_joining_date: String?,
        val employee_type_id: Int?,
        val status: Int?,
        val present_basic_salary: String?,
        val present_gross_salary: String?
    )
}