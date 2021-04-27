package com.dss.hrms.model

class ReportResponse {

    data class VacantPositionSummaryResponse(
        val status: String?,
        val message: String?,
        val code: Int?,
        val data: List<VacantPositionSummary>?
    )

    data class VacantPositionSummary(
        val designation: String?,
        val designation_bn: String?,
        val permitted: Int?,
        val working: Int?,
        val empty: Int?,
        val permanent: Int?,
        val temporary: Int?,
        val priority_order: Int?
    )

}