package com.dss.hrms.di.mainScope

import androidx.fragment.app.FragmentManager
import com.dss.hrms.view.personalinfo.EmployeeInfoActivity
import dagger.Module
import dagger.Provides
import kotlin.reflect.KClass

@Module
class MainModule {

    companion object {

        @MainScope
        @Provides
        fun provideEmployeeInfoActivity(): KClass<EmployeeInfoActivity> {
            return EmployeeInfoActivity::class
        }


        @MainScope
        @Provides
        fun provideFragmentManager(context: EmployeeInfoActivity): FragmentManager {
            return context.supportFragmentManager
        }

//        @MainScope
//        @Provides
//        fun provideEmployeeInfoAdapter(context: Context): EmployeeInfoAdapter {
//            return EmployeeInfoAdapter(context)
//        }

//        @MainScope
//        @Provides
//        fun provideEmployeeInfoDataBinding(): EmployeeInfoDataBinding {
//            return EmployeeInfoDataBinding()
//        }


//        @MainScope
//        @Provides
//        @Named("employee")
//        fun provideEmployee(): Employee {
//            return Employee()
//        }
//
//        @MainScope
//        @Provides
//        @Named("employee-AdditionalQualifications")
//        fun provideAdditionalQualifications(employee: Employee): Employee.AdditionalQualifications {
//            return employee.AdditionalQualifications()
//        }


    }
}