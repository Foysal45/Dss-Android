package com.dss.hrms.model

import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import org.json.JSONObject

data class PermissionResponse(
    val status: String?,
    val code: Int?,
    val data: List<Any>?
)

class JsonKeyReader {
    companion object {
        private fun <T> iterate(i: Iterator<T>): Iterable<T>? {
            return object : Iterable<T> {
                override fun iterator(): Iterator<T> {
                    return i
                }
            }
        }


        fun hasPermission(targetKey: String, data: List<Any>?): Boolean {
            var result = false

            data?.forEach({ element ->
                var jsonString = Gson().toJson(element)
                var jsonObject = JSONObject(jsonString)
                for (key in iterate(jsonObject.keys())!!) {
                    // here key will be containing your OBJECT NAME YOU CAN SET IT IN TEXTVIEW.
                    //   Toast.makeText(this@HomeActivity, "" + key, Toast.LENGTH_SHORT).show()
                    if (targetKey.equals(key)) {
                        Log.e(
                            "result",
                            ".......................result ${key}........................"
                        )
                        return jsonObject.getBoolean(key)
                    }
                }
            })
            return result
        }
    }
}
