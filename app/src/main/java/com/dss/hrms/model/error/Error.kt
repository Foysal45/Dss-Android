package com.chaadride.network.error



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