package com.dss.hrms.retrofit

import com.dss.hrms.model.ReportResponse
import com.dss.hrms.view.training.model.BudgetAndSchedule
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ReportApiService {

    @Headers("Accept: application/json")
    @GET("/api/auth/employee/summery-report")
    suspend fun vacantPositionSummaryReport(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Query("division_id") division_id: Int?,
        @Query("district_id") district_id: Int?,
        @Query("sixteen_category_id") sixteen_category_id: Int?,
        @Query("head_office_department_id") head_office_department_id: Int?,
        @Query("head_office_section_id") head_office_section_id: Int?,
        @Query("head_office_sub_section_id") head_office_sub_section_id: Int?,
        @Query("designation_id") designation_id: Int?,
        @Query("office_id") office_id: Int?,
        @Query("term") term: String?

    ): Response<ReportResponse.VacantPositionSummaryResponse>

}