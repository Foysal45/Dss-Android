package com.dss.hrms.di.messaging

import com.dss.hrms.view.messaging.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MessagingFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeTopMessagingFragment(): TopMessagingFragment

    @ContributesAndroidInjector
    abstract fun contributeMessagingFragment(): MessagingFragment

    @ContributesAndroidInjector
    abstract fun contributeEmailFragment(): EmailFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchEmployeeFragment(): SearchEmployeeFragment


    @ContributesAndroidInjector
    abstract fun contributeMsgListFragment(): MsgListFragment


    @ContributesAndroidInjector
    abstract fun contributeMsgFragment(): MsgFragment


//
//    @ContributesAndroidInjector
//    abstract fun contributeEmployeeBottomSheetFragment(): EmployeeBottomSheetFragment
}