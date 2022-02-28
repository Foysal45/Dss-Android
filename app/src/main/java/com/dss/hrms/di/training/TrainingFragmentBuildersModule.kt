package com.dss.hrms.di.training

import com.dss.hrms.view.training.TrainingFragment
import com.dss.hrms.view.training.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TrainingFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeContentCategoryFragment(): ContentCategoryFragment

    @ContributesAndroidInjector
    abstract fun contributeContentsContentFragment(): ContentsContentFragment

    @ContributesAndroidInjector
    abstract fun contributeContentFaqFragment(): ContentFaqFragment

    @ContributesAndroidInjector
    abstract fun contributeResourcePersonFragment(): ResourcePersonFragment

    @ContributesAndroidInjector
    abstract fun contributeBatchScheduleFragment(): BatchScheduleFragment

    @ContributesAndroidInjector
    abstract fun contributeCourseScheduleFragment(): CourseScheduleFragment

    @ContributesAndroidInjector
    abstract fun contributeTrainingFragment(): TrainingFragment

    @ContributesAndroidInjector
    abstract fun contributeModuleFragment(): ModuleFragment

    @ContributesAndroidInjector
    abstract fun contributeCourseFragment(): CourseFragment


}