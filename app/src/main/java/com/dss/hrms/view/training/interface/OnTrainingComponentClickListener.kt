package com.dss.hrms.view.training

import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.Operation
import com.dss.hrms.util.TrainingModule
import com.dss.hrms.view.training.model.BudgetAndSchedule

interface OnTrainingComponentClickListener {
    fun onShortClick(

        trainingModule: TrainingModule
    )
}