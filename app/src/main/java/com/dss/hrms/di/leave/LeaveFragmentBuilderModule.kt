package com.dss.hrms.di.leave

import com.dss.hrms.view.leave.fragment.CreateEditLeaveApplicationFragment
import com.dss.hrms.view.leave.fragment.LeaveApplicationFragment
import com.dss.hrms.view.leave.fragment.LeaveSummaryFragment
import com.dss.hrms.view.leave.fragment.LeaveTopFragment
import com.dss.hrms.view.messaging.fragment.SearchEmployeeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class LeaveFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeLeaveTopFragment(): LeaveTopFragment

    @ContributesAndroidInjector
    abstract fun contributeLeaveApplicationFragment(): LeaveApplicationFragment

    @ContributesAndroidInjector
    abstract fun contributeLeaveSummaryFragment(): LeaveSummaryFragment


    @ContributesAndroidInjector
    abstract fun contributeSearchEmployeeFragment(): SearchEmployeeFragment

    @ContributesAndroidInjector
    abstract fun contributeCreateEditLeaveApplicationFragment(): CreateEditLeaveApplicationFragment

}