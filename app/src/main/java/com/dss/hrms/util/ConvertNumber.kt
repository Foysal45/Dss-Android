package com.dss.hrms.util

class ConvertNumber {


    fun convertEnglishToBangla(numnber: Int?): String {
        var result = ""
        numnber?.toString()?.toCharArray()?.forEach { ch ->
            when (ch) {
                '0' ->
                    result += "০"
                '1' ->
                    result += "১"

                '2' ->
                    result += "২"
                '3' ->
                    result += "৩"
                '4' ->
                    result += "৪"
                '5' ->
                    result += "৫"
                '6' ->
                    result += "৬"
                '7' ->
                    result += "৭"
                '8' ->
                    result += "৮"
                '9' ->
                    result += "৯"
                else ->
                    result += ch
            }
        }

        return result
    }
}