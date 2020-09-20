package com.dss.hrms.fragment.model

class Box(
    private var title: String,
    private var hint: String,
    private var description: String

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
}