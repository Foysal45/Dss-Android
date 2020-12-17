package com.dss.hrms.view.`interface`

import com.dss.hrms.model.SpinnerDataModel

interface CommonDataValueListener {
    fun valueChange(spinnerDataModel: List<SpinnerDataModel>?);
}