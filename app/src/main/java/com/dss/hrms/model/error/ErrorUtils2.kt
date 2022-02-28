package com.dss.hrms.model.error
import com.dss.hrms.retrofit.RetrofitInstance

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

object ErrorUtils2 {
    fun parseError(response: Response<*>): ApiError {
        val converter: Converter<ResponseBody, ApiError> = RetrofitInstance.retrofitInstance!!.responseBodyConverter(ApiError::class.java, arrayOfNulls<Annotation>(0))
        val error: ApiError
        error = try {
            converter.convert(response.errorBody()!!)!!
        } catch (e: IOException) {
            return ApiError()
        }
        return error
    }

    fun mainError(message: List<String>):String {
        var error=""
        for (n in message.indices)
            error+=message[n]+" "
        return error
    }

}