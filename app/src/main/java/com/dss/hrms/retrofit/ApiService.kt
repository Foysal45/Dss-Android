package com.btbapp.alquranapp.retrofit


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @Headers("Accept: application/json")
    @POST("/api/auth/login")
    fun userLogin(
        @Header("X-Localization") language: String,
        @Body map: HashMap<Any, Any>
    ): Call<Any?>?

    @Headers("Accept: application/json")
    @GET("/api/auth/employee/{Id}")
    fun getEmployeeInfo(
        @Header("Authorization") token: String,
        @Path("Id") employee_id: Int?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/api/auth/logout")
    fun logout(@Header("Authorization") token: String): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/reset-password-otp")
    fun resetPasswordOtp(
        @Header("X-Localization") language: String,
        @Body map: HashMap<Any, Any>
    ): Call<Any>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/verify-otp")
    fun verifyOtp(
        @Header("X-Localization") language: String,
        @Body map: HashMap<Any, Any>
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/reset-password")
    fun resetPass(
        @Header("X-Localization") language: String,
        @Body map: HashMap<Any, Any>
    ): Call<Any?>?


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
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/employee-quota/{Id}")
    fun updateQuotaInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/present/address/{Id}")
    fun updatePresentInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/present/address")
    fun addPresentInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/permanent/address/{Id}")
    fun updatePermanetInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/permanent/address")
    fun addPermanentInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/education-qualification/{Id}")
    fun updateEducationQualificationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/education-qualification")
    fun addEducationQualificationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/spouse/{Id}")
    fun updateSpouseInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/spouse")
    fun addSpouseInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/child/{Id}")
    fun updateChildInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/child")
    fun addChildInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/language/{Id}")
    fun updateLanguageInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/language")
    fun addLanguageInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/local-training/{Id}")
    fun updateLocalTrainingInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/local-training")
    fun addLocalTrainingInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/foreign-training/{Id}")
    fun updateForeignTrainingInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/foreign-training")
    fun addForeignTrainingInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/reference")
    fun addReferenceInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/reference/{Id}")
    fun updateReferenceInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/publication/{Id}")
    fun updatePublicationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/publication")
    fun addPublicationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/official-residential/{Id}")
    fun updateOfficialResidentialInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/official-residential")
    fun addOfficialResidentialInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/foreign-travel/{Id}")
    fun updateForeignTravelInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/foreign-travel")
    fun addForeignTravelInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/honours-award/{Id}")
    fun updateHonoursAwardInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/honours-award")
    fun addHonoursAwardInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("/api/auth/additional-qualification/{Id}")
    fun updateAdditionalQualificationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Path("Id") id: Int?,
        @Body map: HashMap<Any, Any?>?
    ): Call<Any?>?


    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/additional-qualification")
    fun addAdditionalQualificationInfo(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Body map: ArrayList<HashMap<Any, Any?>?>
    ): Call<Any?>?

    @Multipart
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/api/auth/file-upload")
    fun uploadProfilePic(
        @Header("X-Localization") language: String,
        @Header("Authorization") token: String?,
        @Part filenames: MultipartBody.Part?
    ): Call<Any?>?


}