package com.example.dagger_android_practical.di.auth

import com.dss.hrms.model.login.LoginInfo
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class LoginModule {

    /***
     * this is the main usage of access of parent component's dependencies as here we are using Retrofit from appComponent
     * and using that to create our local dependency for local AuthSubComponent
     */
    companion object {
        @LoginScope
        @Provides
        @Named("login-info")
        fun provideAuthUser(): LoginInfo {
            return LoginInfo()
        }
    }

}