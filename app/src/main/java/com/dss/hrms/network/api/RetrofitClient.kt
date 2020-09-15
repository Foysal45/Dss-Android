package com.chaadride.network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    private val RAJUVAI_URL = "http://192.168.10.125:8000/api/"
    private val AWS_URL = "http://dss.aws.simecsystem.com:10012/api/"

    companion object {
        private var mInstance: RetrofitClient? = null

        private var retrofit: Retrofit? = null

        @Synchronized
        fun getInstance(): RetrofitClient {
            if (mInstance == null)
                mInstance =
                    RetrofitClient()
            return mInstance as RetrofitClient
        }
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.MINUTES)
            .build()
        /*        //raju vai URL
                  retrofit = Retrofit.Builder()
                 .baseUrl(RAJUVAI_URL)
                 .client(okHttpClient)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build()  */
        //  AWS URL
        retrofit = Retrofit.Builder()
            .baseUrl(AWS_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

    fun getApi(): Api {
        return retrofit!!.create(Api::class.java)
    }

    fun getRetrofit(): Retrofit {
        return retrofit!!
    }


}