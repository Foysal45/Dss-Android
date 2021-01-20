package com.dss.hrms.view.allInterface

import com.dss.hrms.model.Office

interface OfficeDataValueListener {
    fun valueChange(spinnerDataModel: List<Office>?);
}