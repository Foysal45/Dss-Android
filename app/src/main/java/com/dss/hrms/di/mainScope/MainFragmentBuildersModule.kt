package com.dss.hrms.di.mainScope

import com.dss.hrms.view.personalinfo.fragment.BasicInformationFragment
import com.dss.hrms.view.personalinfo.fragment.FragmentEmployeeInfo
import com.dss.hrms.view.report.fragment.VacantPositionFragment
import com.dss.hrms.view.report.fragment.WorkingEmployeeListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): BasicInformationFragment

    @ContributesAndroidInjector
    abstract fun contributePostsFragment(): FragmentEmployeeInfo
}