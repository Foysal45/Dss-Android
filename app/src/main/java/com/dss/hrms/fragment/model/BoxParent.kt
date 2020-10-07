package com.dss.hrms.fragment.model

class BoxParent(private var child: MutableList<Box>) {
    fun getChild(): MutableList<Box> {
        return child
    }
}