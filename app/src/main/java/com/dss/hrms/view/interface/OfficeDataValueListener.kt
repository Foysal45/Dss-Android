package com.dss.hrms.view.`interface`

import com.dss.hrms.model.Office

interface OfficeDataValueListener {
    fun valueChange(spinnerDataModel: List<Office>?);
}