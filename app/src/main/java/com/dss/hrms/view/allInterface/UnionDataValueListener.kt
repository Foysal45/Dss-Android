package com.dss.hrms.view.allInterface

import com.dss.hrms.model.SpinnerDataModel
import com.dss.hrms.model.Union

interface UnionDataValueListener {
    fun valueChange(unionList: List<Union>?);
}