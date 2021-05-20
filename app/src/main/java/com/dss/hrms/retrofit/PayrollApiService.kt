package com.dss.hrms.retrofit

import com.dss.hrms.model.CustomeResponse
import com.dss.hrms.view.payroll.model.PayrollBankInformationResponse
import com.dss.hrms.view.payroll.model.SalaryGenerateResponse
import retrofit2.Response
import retrofit2.http.*

interface PayrollApiService {

    @Headers("Accept: application/json")
    @GET("/api/auth/payroll-bank-information/list")
    suspend fun bankInfo(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Query("employee_id") employee_id: Int?
    ): Response<PayrollBankInformationResponse>

    @Headers("Accept: application/json")
    @GET("/api/auth/salary-generate")
    suspend fun salaryGenerate(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Query("salary_year") salary_year: String?,
     //   @Query("employee_id") employee_id: String?,
        @Query("salary_month") salary_month: String?
     //   @Query("office_id") office_id: String?
    ): Response<SalaryGenerateResponse>


    @Headers("Accept:application/json")
    @POST("/api/auth/payroll-bank-information")
    suspend fun createBankInformation(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Body map: HashMap<Any, Any?>
    ): Response<CustomeResponse>

    @Headers("Accept:application/json")
    @PUT("/api/auth/payroll-bank-information/{ID}")
    suspend fun updateBankInformation(
        @Header("X-Localization") language: String?,
        @Header("Authorization") token: String,
        @Path("ID") id: Int?,
        @Body map: HashMap<Any, Any?>
    ): Response<CustomeResponse>


}