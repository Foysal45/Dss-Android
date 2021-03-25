package com.dss.hrms.retrofit

import com.dss.hrms.model.CustomeResponse
import com.dss.hrms.view.leave.model.LeaveApplicationApiResponse
import com.dss.hrms.view.leave.model.LeaveSummaryApiResponse
import retrofit2.Response
import retrofit2.http.*

interface LeaveApiService {

    @Headers("Accept: application/json")
    @GET("/api/auth/leave-application/search")
    suspend fun leaveApplicationSearch(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Query("employee_id") employee_id: String?
    ): Response<LeaveApplicationApiResponse.LeaveApplicationResponse>

    @Headers("Accept: application/json")
    @GET("/api/auth/leave-application/leave-summary")
    suspend fun leaveSummary(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String?,
        @Query("employee_id") employee_id: String?
    ): Response<LeaveSummaryApiResponse.LeaveSummaryResponse>

    @Headers("Accept: application/json")
    @GET("/api/auth/leave-policy/list")
    suspend fun leavePolicy(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String?
    ): Response<LeaveApplicationApiResponse.LeavePolicyResponse>


    @Headers("Accept: application/json")
    @POST("/api/auth/leave-application")
    suspend fun createLeaveApplication(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Body map: HashMap<Any, Any?>
    ): Response<CustomeResponse>


    @Headers("Accept: application/json")
    @PUT("/api/auth/leave-application/{ID}")
    suspend fun updateLeaveApplication(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Path("ID") id: Int?,
        @Body map: HashMap<Any, Any?>
    ): Response<CustomeResponse>


}