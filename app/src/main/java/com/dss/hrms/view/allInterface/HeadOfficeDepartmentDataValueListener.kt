package com.dss.hrms.view.allInterface

import com.dss.hrms.model.HeadOfficeDepartmentApiResponse
import com.dss.hrms.model.Office

interface HeadOfficeDepartmentDataValueListener {
    fun valueChange(branches: List<HeadOfficeDepartmentApiResponse.HeadOfficeBranch>?);
}