package com.dss.hrms.view.training.`interface`

import android.media.VolumeShaper
import com.dss.hrms.model.TrainingResponse
import com.dss.hrms.util.Operation

interface OnContentsContentClickListener {
    fun onClick(category: TrainingResponse.ContentsContent, operation: Operation, position: Int?)
}