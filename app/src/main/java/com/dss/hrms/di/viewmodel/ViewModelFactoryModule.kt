package com.example.dagger_android_practical.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.dss.hrms.viewmodel.ViewModelProviderFactory

import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}
