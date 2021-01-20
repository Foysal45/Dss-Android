package com.dss.hrms.view.allInterface

import com.dss.hrms.model.SpinnerDataModel

interface CommonDataValueListener {
    fun valueChange(spinnerDataModel: List<SpinnerDataModel>?);
}