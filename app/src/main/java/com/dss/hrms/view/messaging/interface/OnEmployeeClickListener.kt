package com.dss.hrms.view.messaging.`interface`

import com.dss.hrms.model.RoleWiseEmployeeResponseClass
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.Operation
import com.dss.hrms.view.training.model.BudgetAndSchedule

interface OnEmployeeClickListener {
    fun onClick(
        roleWiseEmployee: RoleWiseEmployeeResponseClass.RoleWiseEmployee,
        position: Int,
        isChecked: Boolean
    )
}