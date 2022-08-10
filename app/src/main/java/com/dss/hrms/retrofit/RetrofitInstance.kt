package com.dss.hrms.retrofit

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    //  var BASE_URL = "https://api.alquran.cloud/"
    //  var BASE_URL = "http://192.168.10.180:8000"
    // var BASE_URL = "http://dss.aws.simecsystem.com:10014"
   //  var BASE_URL = "http://hrmdss.gov.bd:10014"
     var BASE_URL = "http://dss.stage.simecsystem.com:8080"
  //   var BASE_URL = "http://dss.dev.simecsystem.com:10014"
    //  var BASE_URL_FOR_WEBVIEW = "http://dss.dev.simecsystem.com:10015"
   /// var BASE_URL = "http://10.11.105.112:8000";

 //    var BASE_URL = "http://192.168.10.177:8000"
  // var BASE_URL = "http://dss.dev.simecsystem.com:10014"

 //   var BASE_URL_FOR_WEBVIEW = "http://dss.stage.simecsystem.com:8080"
    var BASE_URL_FOR_WEBVIEW = "http://hrmdss.gov.bd"
    var IMAGE_BASE = "http://192.168.10.124:8000/uploads/photos/"
  //  var FILE_BASE = "http://dss.dev.simecsystem.com:10014" 

    //  var BASE_URL = "http://182.160.111.81:10014"
    const val BASE_URL_PLACE_API = "https://maps.googleapis.com/maps/api"
    const val BASE_URL_FCM = "https://fcm.googleapis.com"
    private var retrofit: Retrofit? = null

/*
        playerControlView.setProgressUpdateListener((position, bufferedPosition) -> {

            long newPos = position / 1000;

            if (newPos - prevSec == 1) {
                //    newSec = newSec + 1;
                prevSec = (int) newPos;
                if (newPos % 16 == 0) {
                    callForGift(16);
                    Log.d("BUFFERED", "onCreate: " + newSec + " -> " + prevSec);

                }

            } else {
                prevSec = (int) newPos;
            }

            Log.d("TESTY", "onCreate: " + newPos + "    OLD POS    " + prevSec);


        });
 */

    val retrofitInstance: Retrofit?
        get() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(25, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(OkHttpProfilerInterceptor())
                .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(BASE_URL)
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
