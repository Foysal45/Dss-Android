package com.dss.hrms.di

import android.app.Application
import com.dss.hrms.retrofit.ApiService
import com.dss.hrms.retrofit.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    companion object {

        val TIMEOUT_SECONDS = 60

        @Provides
        @Singleton
        fun provideHttpCache(app: Application): Cache {
            val cacheSize = 10 * 1024 * 1024
            return Cache(app.cacheDir, cacheSize.toLong())
        }

        @Provides
        @Singleton
        fun provideGson(): Gson {
            var gson: Gson = GsonBuilder().setLenient().create()
            return gson
        }

        @Provides
        @Singleton
        fun providesOkHttpClient(cache: Cache): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .addInterceptor(logging)
                .cache(cache)
                // .cache(null)
                .build()
        }

        @Provides
        @Singleton
        fun providesRetrofit(
            @Named("baseUrl") baseUrl: String,
            okHttpClient: OkHttpClient,
            gson: Gson
        ): Retrofit {
            return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        }

        @Provides
        @Singleton
        @Named("retrofit-place")
        fun providesPlaceRetrofitInstance(
            okHttpClient: OkHttpClient
        ): Retrofit {
            return Retrofit.Builder().baseUrl(RetrofitInstance.BASE_URL_PLACE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build()
        }

        @Provides
        @Singleton
        @Named("retrofit-fcm")
        fun providesFcmRetrofitInstance(
            okHttpClient: OkHttpClient,
            gson: Gson
        ): Retrofit {
            return Retrofit.Builder().baseUrl(RetrofitInstance.BASE_URL_FCM)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient).build()
        }

        @Singleton
        @Provides
        fun provideApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }

        @Singleton
        @Provides
        @Named("apiservice-place")
        fun provideApiServiceForPlace(@Named("retrofit-place") retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }

        @Singleton
        @Provides
        @Named("apiservice-fcm")
        fun provideApiServiceForFcm(@Named("retrofit-fcm") retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }

        @Singleton
        @Provides
        fun provideTrainingApiService(retrofit: Retrofit): TrainingApiService {
            return retrofit.create(TrainingApiService::class.java)
        }

        @Singleton
        @Provides
        fun provideMessagingApiService(retrofit: Retrofit): MessagingApiService {
            return retrofit.create(MessagingApiService::class.java)
        }

        @Singleton
        @Provides
        fun provideLeaveApiService(retrofit: Retrofit): LeaveApiService {
            return retrofit.create(LeaveApiService::class.java)
        }

        @Singleton
        @Provides
        fun providePayrollApiService(retrofit: Retrofit): PayrollApiService {
            return retrofit.create(PayrollApiService::class.java)
        }

        @Singleton
        @Provides
        fun provideReportApiServiceApiService(retrofit: Retrofit): ReportApiService {
            return retrofit.create(ReportApiService::class.java)
        }
        @Singleton
        @Provides
        fun provideNotificationApiService(retrofit: Retrofit): NotificationApiService {
            return retrofit.create(NotificationApiService::class.java)
        }
    }
}