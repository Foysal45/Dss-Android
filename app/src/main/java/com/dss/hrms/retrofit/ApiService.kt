package com.btbapp.alquranapp.retrofit


import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.employeeProfile.EmployeeResponse
import com.dss.hrms.model.login.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


interface ApiService {
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/login")
    suspend fun login(
        @Header("X-Localization") language: String,
        @Body map: HashMap<Any, Any>
    ): Response<LoginResponse?>?


    @Headers("Accept: application/json")
    @GET("/api/auth/employee/{Id}")
    suspend fun getEmployeeInfo(
        @Header("Authorization") token: String,
        @Path("Id") employee_id: Int?
    ): Response<EmployeeResponse?>?


    @Headers("Accept: application/json")
    @GET("/api/auth/role-wise-employee")
    suspend fun getRoleWiseEmployeeInfo(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String?,
        @Query("role") role: String?
    ): Response<RoleWiseEmployeeResponseClass.RoleWiseEmployeeRes>?


    @Headers("Accept: application/json")
    @GET("/api/auth/employee")
    suspend fun getEmployeeListAccordingToQuery(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String?,
        @Query("division_id") division_id: String?,
        @Query("district_id") district_id: String?,
        @Query("sixteen_category_id") sixteen_category_id: String?,
        @Query("designation_id") designation_id: String?,
        @Query("office_id") office_id: String?,
        @Query("term") term: String?
    ): Response<RoleWiseEmployeeResponseClass.EmployeeListResponse>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/api/auth/logout")
    fun logout(@Header("Authorization") token: String): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/reset-password-otp")
    suspend fun resetPasswordOtp(
        @Header("X-Localization") language: String,
        @Body map: HashMap<Any, Any>
    ): Response<ResetPasswordReq?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/verify-otp")
    suspend fun verifyOtp(
        @Header("X-Localization") language: String,
        @Body map: HashMap<Any, Any>
    ): Response<VerifyOtp?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/reset-password")
    suspend fun resetPass(
        @Header("X-Localization") language: String,
        @Body map: HashMap<Any, Any>
    ): Response<ResetPassword?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/api/auth/division")
    fun getDivision(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/api/auth/division/{Id}")
    fun getDistrict(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String,
        @Path("Id") divisionId: Int?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/api/auth/district/{Id}")
    fun getUpazila(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String,
        @Path("Id") districtId: Int?
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET
    fun getCommonData(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String,
        @Url url: String
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/job/joining/information/{Id}")
    fun updateJobjoiningInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Observable<Response<Any>?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/employee-quota/{Id}")
    fun updateQuotaInfo1(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Observable<Response<Any>?>?

    @Headers("Content-Type: application/json", "Accept:  /json")
    @PUT("/api/auth/employee-quota/{Id}")
    fun updateQuotaInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/present/address/{Id}")
    suspend fun updatePresentInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/employee/{Id}")
    suspend fun updateBasicInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/present/address")
    suspend fun addPresentInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/permanent/address/{Id}")
    suspend fun updatePermanetInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/permanent/address")
    suspend fun addPermanentInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/education-qualification/{Id}")
    suspend fun updateEducationQualificationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/education-qualification")
    suspend fun addEducationQualificationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/spouse/{Id}")
    suspend fun updateSpouseInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/spouse")
    suspend fun addSpouseInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/child/{Id}")
    suspend fun updateChildInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/child")
    suspend fun addChildInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/language/{Id}")
    suspend fun updateLanguageInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/language")
    suspend fun addLanguageInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/local-training/{Id}")
    suspend fun updateLocalTrainingInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/local-training")
    suspend fun addLocalTrainingInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/foreign-training/{Id}")
    suspend fun updateForeignTrainingInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/foreign-training")
    suspend fun addForeignTrainingInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/reference")
    suspend fun addReferenceInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/reference/{Id}")
    suspend fun updateReferenceInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/publication/{Id}")
    suspend fun updatePublicationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/publication")
    suspend fun addPublicationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/official-residential/{Id}")
    suspend fun updateOfficialResidentialInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/official-residential")
    suspend fun addOfficialResidentialInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/foreign-travel/{Id}")
    suspend fun updateForeignTravelInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/foreign-travel")
    suspend fun addForeignTravelInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/honours-award/{Id}")
    suspend fun updateHonoursAwardInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/honours-award")
    suspend fun addHonoursAwardInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/additional-qualification/{Id}")
    suspend fun updateAdditionalQualificationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Response<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/additional-qualification")
    suspend fun addAdditionalQualificationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Response<Any?>?

    @Multipart
    @POST("/api/auth/file-upload")
    fun uploadProfilePic(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Part("type ") type: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<Any?>?

}