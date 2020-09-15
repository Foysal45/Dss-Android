package com.dss.hrms.activity.ac_main.model

import com.dss.hrms.R

class Dashboard(
    private var dashBoardName: String,
    private var dashBoardImage: Int

) {

    fun getDashBoardName(): String {
        if (dashBoardName.equals(null))
            return ""
        return dashBoardName
    }
    fun getDashBoardImage(): Int {
        if (dashBoardImage.equals(null))
            return R.drawable.ic_dashboard
        return dashBoardImage
    }
}