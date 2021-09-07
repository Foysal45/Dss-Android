package com.dss.hrms.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.dss.hrms.R
import com.dss.hrms.retrofit.RetrofitInstance
import java.lang.Exception

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

    companion object {

        fun setIconOnTextView( icon : ImageView , textview  : TextView ,  link : String? , ctx: Context  ){

            val extentions = getTheFileExtention(link)

           textview.text = (ctx.getString(R.string.tap_to_view))

            if (extentions.contains("jpeg") || extentions.contains("jpg") || extentions.contains("gif")) {

                icon.setImageResource(R.drawable.ic_picture)

            } else {
                icon.setImageResource(R.drawable.ic_pdf)
            }

        }

        fun viewFileInShareIntent(ctx: Context, link: String) {
            try {
                if (link.isNullOrBlank() || link.contains("null")) {
                    Toast.makeText(ctx, "Something Went Wrong !!", Toast.LENGTH_LONG).show()
                } else {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(RetrofitInstance.FILE_BASE + link.toString())
                    )
                    ctx.startActivity(browserIntent)
                }
            } catch (Ex: Exception) {
                Toast.makeText(ctx, "Something Went Wrong !!", Toast.LENGTH_LONG).show()
            }

        }

        fun getTheFileNameFromTheLink(link: String?): String {

            return if (link.isNullOrBlank()) {
                "No File Found"
            } else {
                link.substring(link.lastIndexOf('/') + 1)
            }
        }

        fun getTheFileExtention(link: String?): String {
            return try {
                if (link.isNullOrBlank()) {
                    "No File Found"
                } else {
                    link.substring(link.lastIndexOf("."))
                }
            } catch (ex: Exception) {
                "No File"
            }
        }

    }

}

