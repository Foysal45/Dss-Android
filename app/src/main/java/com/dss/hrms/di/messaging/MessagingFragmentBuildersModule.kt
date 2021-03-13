package com.dss.hrms.di.messaging

import com.dss.hrms.view.fragment.BasicInformationFragment
import com.dss.hrms.view.fragment.FragmentEmployeeInfo
import com.dss.hrms.view.messaging.fragment.*
import com.dss.hrms.view.training.fragment.*
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
//
//    @ContributesAndroidInjector
//    abstract fun contributeEmployeeBottomSheetFragment(): EmployeeBottomSheetFragment
}