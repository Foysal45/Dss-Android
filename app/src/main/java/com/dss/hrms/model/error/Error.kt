package com.dss.hrms.model.error
class Error (
    private var field: String,
    private var message: List<String>
){

    fun getField(): String {
        if (field.equals(null))
            return ""
        return field
    }
    fun getMessage(): List<String> {
        return message
    }
}