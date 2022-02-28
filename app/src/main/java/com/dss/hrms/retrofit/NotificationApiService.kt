package com.dss.hrms.retrofit

import com.dss.hrms.model.employeeProfile.EmployeeResponse
import com.dss.hrms.view.notification.model.NotificationResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface NotificationApiService {


    @Headers("Accept: application/json")
    @GET("/api/auth/employee-notification")
    suspend fun getAllNotifications(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String?,
        @Query("platform") platform: String?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): Response<NotificationResponse?>?

    @Headers("Accept: application/json")
    @GET("/api/auth/employee-notification")
    fun getAllNotificationsWithoutCorotuins(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String?,
        @Query("platform") platform: String?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): Call<Any?>?

    @Headers("Accept: application/json")
    @GET("/api/auth/employee-notification")
    fun getAllNotificationsWithoutCorotuins1(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String?,
        @Query("platform") platform: String?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): Call<NotificationResponse?>?
}