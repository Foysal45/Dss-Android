package com.dss.hrms.di.payroll

import com.dss.hrms.view.payroll.fragment.EmployeeBankInformationFragment
import com.dss.hrms.view.payroll.fragment.EmployeeSalaryProcessFragment
import com.dss.hrms.view.payroll.fragment.PayrollTopFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PayrollFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributePayrollTopFragment(): PayrollTopFragment

    @ContributesAndroidInjector
    abstract fun contributeEmployeeSalaryProcessFragment(): EmployeeSalaryProcessFragment

    @ContributesAndroidInjector
    abstract fun contributeEmployeeBankInformationFragment(): EmployeeBankInformationFragment
}