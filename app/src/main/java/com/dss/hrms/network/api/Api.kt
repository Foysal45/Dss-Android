package com.chaadride.network.api

import com.dss.hrms.network.model.honours_award.response.HonorAwardRes
import com.dss.hrms.network.model.user_login.request.UserLoginReq
import com.dss.hrms.network.model.user_login.response.UserLoginRes
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("auth/login")
    fun UserLoginRes_(
        @Header("Content-Type") Content_Type: String,
        @Header("Accept") Accept: String,
        @Body userLoginReq: UserLoginReq
    ): Call<UserLoginRes>

    @POST("auth/honours-award/?employee_id=1")
    fun HonorAwardRes_(
        @Header("Content-Type") Content_Type: String,
        @Header("Accept") Accept: String,
        @Header("Authorization") token: String,
        @Query("employee_id") employee_id: Int
    ): Call<HonorAwardRes>


}


