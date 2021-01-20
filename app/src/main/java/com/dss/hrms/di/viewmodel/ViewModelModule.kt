package com.dss.hrms.di.viewmodel

import androidx.lifecycle.ViewModel
import com.dss.hrms.viewmodel.EmployeeInfoEditCreateViewModel
import com.dss.hrms.viewmodel.EmployeeViewModel
import com.dss.hrms.viewmodel.LoginViewModel
import com.dss.hrms.viewmodel.UtilViewModel
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
    @ViewModelKey(EmployeeViewModel::class)
    abstract fun bindEmployeeViewModel(authViewModel: EmployeeViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(EmployeeInfoEditCreateViewModel::class)
    abstract fun bindEmployeeInfoEditCreateViewModel(authViewModel: EmployeeInfoEditCreateViewModel): ViewModel

}