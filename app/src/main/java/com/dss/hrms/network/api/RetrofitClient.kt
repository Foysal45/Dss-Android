package com.chaadride.network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    private val RAJUVAI_URL ="http://192.168.10.125:8000/api/"

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

        retrofit = Retrofit.Builder()
            .baseUrl(RAJUVAI_URL)
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