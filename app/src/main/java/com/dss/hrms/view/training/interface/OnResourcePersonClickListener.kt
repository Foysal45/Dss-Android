package com.dss.hrms.view.training.`interface`

import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.Operation

interface OnResourcePersonClickListener {
    fun onClick(
        resourcePerson: TrainingResponse.ResourcePerson,
        position: Int,
        operation: Operation
    )
}