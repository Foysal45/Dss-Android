package com.dss.hrms.util

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.dss.hrms.R
import com.dss.hrms.retrofit.RetrofitInstance
import java.lang.Exception
import android.util.Log

import android.webkit.WebView

import android.webkit.WebViewClient
import android.view.View
import androidx.appcompat.app.AlertDialog
import java.io.File


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
        const val FLAGS_FULLSCREEN =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        fun setIconOnTextView(icon: ImageView, textview: TextView, link: String?, ctx: Context) {
            Log.d("TAG", "documnet link: $link")
            val extentions = getTheFileExtention(link)

            textview.text = (ctx.getString(R.string.tap_to_view))

            if (link.equals("null") || link.isNullOrBlank() || link.toString().length < 4) {
                textview.text = ctx.getString(R.string.no_attachment)
                textview.setTextColor(Color.DKGRAY)
            } else if (extentions.contains("jpeg") || extentions.contains("jpg") || extentions.contains(
                    "gif"
                )
            ) {

                icon.setImageResource(R.drawable.ic_picture)

            } else {
                icon.setImageResource(R.drawable.ic_pdf)
            }

        }


        fun triggerWebView(ctx: Context, link: String) {
            val PDfBase = "https://docs.google.com/viewer?url="
            val alert = Dialog(ctx, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
            alert.setContentView(R.layout.fullscreen_web_view)

            alert.setTitle("Document")

            val wv: WebView = alert.findViewById(R.id.webView)
            val closeImage: ImageView = alert.findViewById(R.id.closeBtn)

            wv.settings.javaScriptEnabled = true
            wv.webViewClient = WebViewClient()
            wv.settings.loadWithOverviewMode = true
            wv.settings.useWideViewPort = true
            val lik = if (getTheFileExtention(link)
                    .contains("pdf") || getTheFileExtention(link).contains("docx")
            ) {
                PDfBase + RetrofitInstance.BASE_URL + link
            } else {
                RetrofitInstance.BASE_URL + link
            }
            wv.loadUrl(lik)

            wv.webViewClient = object : WebViewClient() {
                //                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                    view?.loadUrl(lik)
//                    return true
//                }
                override fun onPageFinished(view: WebView, url: String) {
                    if (view.title.equals("")) {
                        view.reload()
                    }
                    super.onPageFinished(view, url)
                }
            }
            wv.clearCache(true)
            wv.settings.javaScriptEnabled = true
            Log.d("TAG", "triggerWebView: " + lik)

            closeImage.setOnClickListener {
                alert.dismiss()
            }
            alert.setCancelable(true)
            alert.show()
        }

        fun viewFileInShareIntent(ctx: Context, link: String?) {
            try {
                if (link.isNullOrBlank() || link == "null") {
                    Toast.makeText(ctx, "Something Went Wrong !! link empty", Toast.LENGTH_LONG)
                        .show()
                } else {

                    triggerWebView(ctx, link)
//                    val browserIntent = Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse(RetrofitInstance.FILE_BASE + link.toString())
//                    )
//                    ctx.startActivity(browserIntent)
                }
            } catch (Ex: Exception) {
                Toast.makeText(
                    ctx,
                    "Something Went Wrong !! ${Ex.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
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


        fun isFileLessThan2MB(file: File?): Boolean {
            val maxFileSize = 2 * 1024 * 1024
            val l = file?.length()
            val fileSize = l.toString()
            val finalFileSize = fileSize.toInt()
            return finalFileSize <= maxFileSize
        }

        fun errorDialogueWithProgressBar(context: Context, string: String) {

            val progreesDialog = ProgressDialog(context)
            progreesDialog.setMessage("Uploading...")
            progreesDialog.show()
            progreesDialog.setCancelable(false)

            val alertDialog = AlertDialog.Builder(context)
            Handler(Looper.getMainLooper()).postDelayed({
                progreesDialog.dismiss()
                alertDialog.apply {
                    setIcon(R.drawable.ic_baseline_notifications_24)
                    setTitle(context.getString(R.string.error))
                    setMessage(string)
                    setPositiveButton("Ok") { _, _ ->

                    }
                }.create().show()
            }, 1000)


        }

    }


}

