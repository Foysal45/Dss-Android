package com.dss.hrms.retrofit

import com.dss.hrms.model.employeeProfile.EmployeeResponse
import com.dss.hrms.view.notification.model.NotificationResponse
import retrofit2.Response
import retrofit2.http.*

interface NotificationApiService {


    @Headers("Accept: application/json")
    @GET("/api/auth/employee-notification")
    suspend fun getAllNotifications(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String?,
        @Query("platform") platform: String?
    ): Response<NotificationResponse?>?
}