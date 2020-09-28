package com.dss.hrms.fragment.model

import android.view.View

class Box(
    private var title: String,
    private var hint: String,
    private var description: String,
    private var spaceShow: Int=View.GONE

) {


    fun getTitle(): String {
        if (title.equals(null))
            return ""
        return title
    }
    fun getHint(): String {
        if (hint.equals(null))
            return ""
        return hint
    }
    fun getDescription(): String {
        if (description.equals(null))
            return ""
        return description
    }
    fun getSpaceShow(): Int {
        return spaceShow
    }
}