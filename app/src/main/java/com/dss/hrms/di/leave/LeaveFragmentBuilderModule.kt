package com.dss.hrms.di.leave

import com.dss.hrms.view.leave.fragment.LeaveApplicationFragment
import com.dss.hrms.view.leave.fragment.LeaveSummaryFragment
import com.dss.hrms.view.leave.fragment.LeaveTopFragment
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

}