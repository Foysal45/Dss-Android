package com.dss.hrms.view.training.model

data class ExpertiseResponse(val code: Int?, val data: List<ExpertiseField>?)
data class ExpertiseField(
    val id: Int?,
    val expertise_name: String?,
    val expertise_name_bn: String?,
    val status: Int?
)