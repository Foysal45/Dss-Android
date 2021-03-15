package com.dss.hrms.retrofit

import com.dss.hrms.model.CustomeResponse
import com.dss.hrms.model.TrainingResponse
import retrofit2.Response
import retrofit2.http.*

interface MessagingApiService {

    @Headers("Accept: application/json")
    @POST("/api/auth/send-email-notification")
    suspend fun sendEmail(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Body map: HashMap<Any, Any?>
    ): Response<CustomeResponse>

    @Headers("Accept: application/json")
    @POST("/api/auth/send-sms-notification")
    suspend fun sendSms(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Body map: HashMap<Any, Any?>
    ): Response<CustomeResponse>

}