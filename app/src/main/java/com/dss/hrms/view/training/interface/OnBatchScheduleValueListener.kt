package com.dss.hrms.view.training

import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.view.training.model.BudgetAndSchedule

interface OnBatchScheduleValueListener {
    fun onValueChange(batchScheduleList: List<BudgetAndSchedule.BatchSchedule>?)
}