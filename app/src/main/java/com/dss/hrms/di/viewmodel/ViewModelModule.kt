package com.dss.hrms.di.viewmodel

import androidx.lifecycle.ViewModel
import com.dss.hrms.view.leave.viewmodel.LeaveApplicationViewmodel
import com.dss.hrms.view.leave.viewmodel.LeaveSummaryViewModel
import com.dss.hrms.view.messaging.viewmodel.MessagingViewModel
import com.dss.hrms.view.payroll.viewmodel.PayRollBankInformationViewModel
import com.dss.hrms.view.payroll.viewmodel.SalaryGenerateViewModel
import com.dss.hrms.view.report.viewmodel.CommonReportViewModel
import com.dss.hrms.view.training.viewmodel.BudgetAndScheduleViewModel
import com.dss.hrms.view.training.viewmodel.ContentManagementViewModel
import com.dss.hrms.view.training.viewmodel.TrainingManagementViewModel
import com.dss.hrms.view.training.viewmodel.TrainingSimpleViewModel
import com.dss.hrms.viewmodel.*
import com.example.dagger_android_practical.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UtilViewModel::class)
    abstract fun bindUtilViewModel(authViewModel: UtilViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CommonViewModel::class)
    abstract fun bindCommonViewModel(commonViewModel: CommonViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(EmployeeViewModel::class)
    abstract fun bindEmployeeViewModel(authViewModel: EmployeeViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(EmployeeInfoEditCreateViewModel::class)
    abstract fun bindEmployeeInfoEditCreateViewModel(employeeViewEditCreateViewModel: EmployeeInfoEditCreateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContentManagementViewModel::class)
    abstract fun bindContentManagementViewModel(contentManagementViewModel: ContentManagementViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingManagementViewModel::class)
    abstract fun bindTrainingManagementViewModel(trainingManagementViewModel: TrainingManagementViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(BudgetAndScheduleViewModel::class)
    abstract fun bindBudgetAndScheduleViewModel(budgetAndScheduleViewModel: BudgetAndScheduleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingSimpleViewModel::class)
    abstract fun bindTrainingSimpleViewModel(trainingSimpleViewModel: TrainingSimpleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessagingViewModel::class)
    abstract fun bindMessagingViewModel(messagingViewModel: MessagingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PayRollBankInformationViewModel::class)
    abstract fun bindPayRollBankInformationViewModel(payRollBankInformationViewModel: PayRollBankInformationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SalaryGenerateViewModel::class)
    abstract fun bindPayRollSalaryGenerateViewModel(payRollSalaryGenerateViewModel: SalaryGenerateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LeaveApplicationViewmodel::class)
    abstract fun bindLeaveApplicationViewmodel(leaveApplicationViewmodel: LeaveApplicationViewmodel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LeaveSummaryViewModel::class)
    abstract fun bindLeaveSummaryViewModel(leaveSummaryViewModel: LeaveSummaryViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(CommonReportViewModel::class)
    abstract fun bindCommonReportViewModel(commonReportViewModel: CommonReportViewModel): ViewModel

}