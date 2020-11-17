package com.chaadride.network.error
import com.google.gson.annotations.SerializedName

class ApiError (
){
    @SerializedName("message")
    private var message: String? =null

    @SerializedName("errors")
    private var errors: List<Error>? =null



    fun getMessage(): String {
        if (message.equals(null)) {
            return ""
        }
        return message!!
    }

    fun getError(): List<Error> {
        return errors!!
    }

}
