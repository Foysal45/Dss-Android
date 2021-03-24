package com.dss.hrms.view.leave.`interface`

import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.Operation
import com.dss.hrms.view.training.model.BudgetAndSchedule

interface OnLeaveApplicationClickListener {
    fun onClick(
        any: Any?,
        position: Int,
        operation: Operation
    )
}