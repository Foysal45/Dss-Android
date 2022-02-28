package com.dss.hrms.view.training.`interface`

import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.Operation
import com.dss.hrms.view.training.model.BudgetAndSchedule

interface OnBatchScheduleClickListener {
    fun onClick(
        batchSchedule: BudgetAndSchedule.BatchSchedule?,
        position: Int,
        operation: Operation
    )
}