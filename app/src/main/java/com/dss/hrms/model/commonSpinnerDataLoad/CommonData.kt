package com.dss.hrms.model.commonSpinnerDataLoad

import com.dss.hrms.model.Paysacle

data class CommonData(
    val genders: List<CommonModel> = mutableListOf(),
    val marital_status: List<CommonModel> = mutableListOf(),
    val religions: List<CommonModel> = mutableListOf(),
    val employee_types: List<CommonModel> = mutableListOf(),
    val employment_status_types: List<CommonModel> = mutableListOf(),
    val disability_types: List<CommonModel> = mutableListOf(),
    val disability_degrees: List<CommonModel> = mutableListOf(),
    val offices: List<CommonModel> = mutableListOf(),
    val designations: List<CommonModel> = mutableListOf(),
    val job_types: List<CommonModel> = mutableListOf(),
    val employee_classes: List<CommonModel> = mutableListOf(),
    val pay_scales: List<Paysacle> = mutableListOf(),
    val salary_grades: List<CommonModel> = mutableListOf(),
    val divisions: List<CommonModel> = mutableListOf(),
    val quota_information: List<CommonModel> = mutableListOf(),
    val districts: List<CommonModel> = mutableListOf(),
    val quota_types: List<CommonModel> = mutableListOf(),
    val upazilas: List<CommonModel> = mutableListOf(),
    val zilla_municipalities: List<CommonModel> = mutableListOf(),
    val upazilla_municipalities: List<CommonModel> = mutableListOf(),
    val unions: List<CommonModel> = mutableListOf(),
    val city_corporations: List<CommonModel> = mutableListOf(),
    val examinations: List<CommonModel> = mutableListOf(),
    val boards: List<CommonModel> = mutableListOf(),
    val educational_institutes: List<CommonModel> = mutableListOf(),
    val occupations: List<CommonModel> = mutableListOf(),
    val spouse_workstations: List<CommonModel> = mutableListOf(),
    val spouse_job_types: List<CommonModel> = mutableListOf(),
    val training_categories: List<CommonModel> = mutableListOf(),
    val countries: List<CommonModel> = mutableListOf(),
    val publication_types: List<CommonModel> = mutableListOf(),
    val disciplinary_action_category: List<CommonModel> = mutableListOf(),

)
