package com.dss.hrms.di

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.dss.hrms.R
import com.dss.hrms.model.login.LoginInfo
import com.google.gson.Gson
import com.namaztime.namaztime.database.MySharedPreparence

import dagger.Module
import dagger.Provides

import javax.inject.Singleton

/***
 * We keep our dependencies here and then through Components(Servers) they are served to different clients
 *
 * Put all your app level dependencies here like Retrofit, DBManager, SessionManager, NetworkChangeManager, SharedPrefs, ImageLoader(Glide)
 *
 * Using static(Companions) is preferred as Dagger can internally call these without creating an object of this class
 */
@Module
class AppModule {

    companion object {
        @Singleton
        @Provides
        fun provideRequestOptions(): RequestOptions {
            return RequestOptions.placeholderOf(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
        }

        @Singleton
        @Provides
        fun provideGlideInstance(
            application: Application,
            requestOptions: RequestOptions
        ): RequestManager {
            return Glide.with(application).setDefaultRequestOptions(requestOptions)
        }


        @Singleton
        @Provides
        fun provideSharedPreparence(
            application: Application
        ): MySharedPreparence {
            return MySharedPreparence(application)
        }

//        @Singleton
//        @Provides   // @Binds, binds the Application instance to Context
//        fun context(appInstance: Application): Context {
//            return appInstance
//        } //just return the super-type you need

        @Singleton
        @Provides
        fun provideLoginInfo(
            preparence: MySharedPreparence
        ): LoginInfo {
            return Gson().fromJson(preparence.getLoginInfo(), LoginInfo::class.java)
        }

//        @Singleton
//        @Provides
//        fun provideEmployeeProfileData(): EmployeeProfileData {
//            return EmployeeProfileData()
//        }

//        @Singleton
//        @Provides
//        fun provideEmployee(employeeProfileData: EmployeeProfileData): Employee {
//            if (employeeProfileData.employee == null)
//                employeeProfileData.employee = Employee()
//            return employeeProfileData.employee!!
//        }

//        @Singleton
//        @Provides
//        fun provideEmployee(): Employee {
//            return Employee()
//        }
//
//        @Singleton
//        @Provides
//        fun provideAdditionalQualifications(employee: Employee): Employee.AdditionalQualifications {
//            return employee.AdditionalQualifications()
//        }


//        @Singleton
//        @Provides
//        @Named("test")
//        fun provideTest():Test{
//            return
//        }

    }
}