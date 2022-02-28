package com.dss.hrms.view.training.`interface`

import com.dss.hrms.model.TrainingResponse

interface OnResourcePersonValueListener {
    fun onValueChange(resourcePersonList: List<TrainingResponse.ResourcePerson>?)
}