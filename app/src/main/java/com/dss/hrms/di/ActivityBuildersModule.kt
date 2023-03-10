package com.dss.hrms.di

import com.dss.hrms.SplashScreen
import com.dss.hrms.di.leave.LeaveFragmentBuilderModule
import com.dss.hrms.di.leave.LeaveScope
import com.dss.hrms.di.mainScope.MainFragmentBuildersModule
import com.dss.hrms.di.mainScope.MainModule
import com.dss.hrms.di.mainScope.MainScope
import com.dss.hrms.di.messaging.MessagingFragmentBuildersModule
import com.dss.hrms.di.messaging.MessagingScope
import com.dss.hrms.di.payroll.PayrollFragmentBuilderModule
import com.dss.hrms.di.payroll.PayrollScope
import com.dss.hrms.di.report.ReportFragmentBuilderModule
import com.dss.hrms.di.training.TrainingFragmentBuildersModule
import com.dss.hrms.di.training.TrainingScope
import com.dss.hrms.di.viewmodel.ViewModelModule
import com.dss.hrms.view.MainActivity
import com.dss.hrms.view.activity.*
import com.dss.hrms.view.leave.LeaveActivity
import com.dss.hrms.view.auth.ChangePassActivity
import com.dss.hrms.view.auth.ForgetPAssActivity
import com.dss.hrms.view.auth.LoginActivity
import com.dss.hrms.view.auth.OTPActivity
import com.dss.hrms.view.messaging.MessagingActivity
import com.dss.hrms.view.notification.NotificationActivity
import com.dss.hrms.view.payroll.PayrollActivity
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import com.dss.hrms.view.report.ReportActivity
import com.dss.hrms.view.settings.SettingsActivity
import com.dss.hrms.view.training.TrainingActivity
import com.example.dagger_android_practical.di.auth.LoginModule
import com.example.dagger_android_practical.di.auth.LoginScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/***
 * this will be strictly used to inject activities only
 */
@Module
abstract class ActivityBuildersModule {
    @MainScope
    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class, MainModule::class, ViewModelModule::class])
    abstract fun contributeSplashScreen(): SplashScreen

    @LoginScope
    @ContributesAndroidInjector(modules = [LoginModule::class, ViewModelModule::class])
    abstract fun contributeAuthActivity(): LoginActivity

    @LoginScope
    @ContributesAndroidInjector(modules = [LoginModule::class, ViewModelModule::class])
    abstract fun contributeForgetPasswordActivity(): ForgetPAssActivity

    @LoginScope
    @ContributesAndroidInjector(modules = [LoginModule::class, ViewModelModule::class])
    abstract fun contributeOtpActivity(): OTPActivity

    @LoginScope
    @ContributesAndroidInjector(modules = [LoginModule::class, ViewModelModule::class])
    abstract fun contributeChangePassActivity(): ChangePassActivity

    @MainScope
    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class, MainModule::class, ViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @MainScope
    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class, MainModule::class, ViewModelModule::class])
    abstract fun contributeEmployeeInfoActivity(): EmployeeInfoActivity

    @MainScope
    @ContributesAndroidInjector(modules = [MainModule::class, ViewModelModule::class])
    abstract fun contributeSettingsActivity(): SettingsActivity






    @MainScope
    @ContributesAndroidInjector(modules = [MainModule::class, ViewModelModule::class])
    abstract fun contributeNotificationActivity(): NotificationActivity

    @TrainingScope
    @ContributesAndroidInjector(modules = [TrainingFragmentBuildersModule::class, ViewModelModule::class])
    abstract fun contributeTrainingActivity(): TrainingActivity

    @MessagingScope
    @ContributesAndroidInjector(modules = [MessagingFragmentBuildersModule::class, ViewModelModule::class])
    abstract fun contributeMessagingActivity(): MessagingActivity

    @LeaveScope
    @ContributesAndroidInjector(modules = [LeaveFragmentBuilderModule::class, ViewModelModule::class])
    abstract fun contributeLeaveActivity(): LeaveActivity

    @LeaveScope
    @ContributesAndroidInjector(modules = [ReportFragmentBuilderModule::class, ViewModelModule::class])
    abstract fun contributeReportActivity(): ReportActivity


    @PayrollScope
    @ContributesAndroidInjector(modules = [PayrollFragmentBuilderModule::class, ViewModelModule::class])
    abstract fun contributePayrollActivity(): PayrollActivity

    @TrainingScope
    @ContributesAndroidInjector(modules = [TrainingFragmentBuildersModule::class, ViewModelModule::class])
    abstract fun contributeWebviewActivity(): WebViewActivity

}