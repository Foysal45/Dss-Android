package com.dss.hrms.view.training.`interface`

import android.media.VolumeShaper
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.Operation

interface OnContentCategoryClickListener {
    fun onClick(category: TrainingResponse.Category, operation: Operation, position: Int?)
}