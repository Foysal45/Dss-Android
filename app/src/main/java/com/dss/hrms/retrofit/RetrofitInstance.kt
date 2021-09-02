package com.dss.hrms.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    //  var BASE_URL = "https://api.alquran.cloud/"
    //  var BASE_URL = "http://192.168.10.180:8000"
    // var BASE_URL = "http://dss.aws.simecsystem.com:10014"
    //var BASE_URL = "http://dss.aws.simecsystem.com:10014"
    //var BASE_URL = "http://dss.stage.simecsystem.com:10014"
 //   var BASE_URL = "http://dss.dev.simecsystem.com:10014"
  //  var BASE_URL_FOR_WEBVIEW = "http://dss.dev.simecsystem.com:10015"
   // var BASE_URL = "http://192.168.10.134:8000"

  // var BASE_URL = "http://dss.stage.simecsystem.com:10014"
   var BASE_URL = "http://dss.dev.simecsystem.com:10014"

   var BASE_URL_FOR_WEBVIEW = "http://dss.stage.simecsystem.com:10015"
    //  var BASE_URL = "http://182.160.111.81:10014"
    const val BASE_URL_PLACE_API = "https://maps.googleapis.com/maps/api"
    const val BASE_URL_FCM = "https://fcm.googleapis.com"
    private var retrofit: Retrofit? = null


    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(25, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(25, TimeUnit.SECONDS)
        .build()

    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(
                    BASE_URL
                )
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient).build()
            }
            return retrofit
        }

    val retrofitInstanceForPlaceAPI: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit =
                    Retrofit.Builder().baseUrl(BASE_URL_PLACE_API)
                        .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }

    val retrofitInstanceForFCM: Retrofit?
        get() {

            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(
                    BASE_URL_FCM
                )
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }
}
