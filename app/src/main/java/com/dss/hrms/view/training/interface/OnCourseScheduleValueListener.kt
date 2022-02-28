package com.dss.hrms.view.training

import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.view.training.model.BudgetAndSchedule

interface OnCourseScheduleValueListener {
    fun onValueChange(courseScheduleList: List<BudgetAndSchedule.CourseSchedule>?)
}