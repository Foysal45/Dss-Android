package com.dss.hrms

import com.dss.hrms.di.DaggerAppComponent
import com.dss.hrms.retrofit.RetrofitInstance
import com.dss.hrms.util.StaticKey
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {

    //this method returns our appComponent, previously we used to create appComponent here by not extending the DaggerApplication
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerAppComponent.builder().application(this)
            .baseUrl(RetrofitInstance.BASE_URL).build()
    }
}