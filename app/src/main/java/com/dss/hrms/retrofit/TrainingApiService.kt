package com.dss.hrms.retrofit

import com.dss.hrms.model.CustomeResponse
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.view.training.model.BudgetAndSchedule
import retrofit2.Response
import retrofit2.http.*

interface TrainingApiService {
    @Headers("Accept: application/json")
    @GET("/api/auth/category/list")
    suspend fun category(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String
    ): Response<TrainingResponse.ContentCategory>

    @Headers("Accept: application/json")
    @PUT("/api/auth/category/{categoryId}")
    suspend fun updateCategory(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Path("categoryId") categoryId: Int,
        @Body map: HashMap<Any, Any>?
    ): Response<Any>

    @Headers("Accept: application/json")
    @GET("/api/auth/content/list")
    suspend fun content(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String
    ): Response<TrainingResponse.ContentsContent>

    @Headers("Accept: application/json")
    @GET("/api/auth/faq")
    suspend fun faq(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String
    ): Response<TrainingResponse.ContentsFaq>

    @Headers("Accept: application/json")
    @GET("/api/auth/resource-person/list")
    suspend fun resourcePerson(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String
    ): Response<TrainingResponse.ResourcePersonResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/resource-person/{ID}")
    suspend fun updateResourcePerson(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Path("ID") ID: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<CustomeResponse>

    @Headers("Accept: application/json")
    @GET("/api/auth/batch-schedule")
    suspend fun batchSchedule(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String
    ): Response<BudgetAndSchedule.BatchScheduleResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/batch-schedule/{ID}")
    suspend fun updateBatchSchedule(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Path("ID") ID: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<CustomeResponse>

    @Headers("Accept: application/json")
    @GET("/api/auth/course-schedule")
    suspend fun courseSchedule(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String
    ): Response<BudgetAndSchedule.CourseScheduleResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/course-schedule/{ID}")
    suspend fun updateCourseSchedule(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Path("ID") ID: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<CustomeResponse>

}