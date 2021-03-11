package com.dss.hrms.retrofit

import com.dss.hrms.model.TrainingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface MessagingApiService {

    @Headers("Accept: application/json")
    @GET("/api/auth/category/list")
    suspend fun category(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String
    ): Response<TrainingResponse.ContentCategory>

}