package com.dss.hrms.di.report

import com.dss.hrms.view.leave.fragment.CreateEditLeaveApplicationFragment
import com.dss.hrms.view.leave.fragment.LeaveApplicationFragment
import com.dss.hrms.view.leave.fragment.LeaveSummaryFragment
import com.dss.hrms.view.leave.fragment.LeaveTopFragment
import com.dss.hrms.view.messaging.fragment.SearchEmployeeFragment
import com.dss.hrms.view.report.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ReportFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeTopReportFragment(): TopReportFragment

    @ContributesAndroidInjector
    abstract fun contributeVacantPositionFragment(): VacantPositionFragment

    @ContributesAndroidInjector
    abstract fun contributeWorkingEmployeeListFragment(): WorkingEmployeeListFragment

    @ContributesAndroidInjector
    abstract fun contributePdfViewerFragment(): PdfViewerFragment

    @ContributesAndroidInjector
    abstract fun contributeVacantPositionReportShowingFragment(): VacantPositionReportShowingFragment


}