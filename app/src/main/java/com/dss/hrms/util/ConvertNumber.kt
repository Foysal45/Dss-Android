package com.dss.hrms.util

import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
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
import com.dss.hrms.view.MainActivity
import java.io.File
import java.io.FileOutputStream
import android.content.ContentResolver





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
                ) || extentions.contains("png")
            ) {
                icon.setImageResource(R.drawable.ic_picture)

            } else if (extentions.contains("docx") || extentions.contains("doc")) {
                icon.setImageResource(R.drawable.ic_doc)
            } else if (extentions.contains("pdf") || extentions.contains("PDF")) {
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

                    //          triggerWebView(ctx, link)
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(RetrofitInstance.BASE_URL + link.toString())
                    )
                    MainActivity.isViewIntent = 1
                    ctx.startActivity(browserIntent)
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
            Log.d("TAG", "isFileLessThan2MB:  $l")
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

        fun getRealPathFromURI(context: Context, uri: Uri): String? {
            when {
                // DocumentProvider
                DocumentsContract.isDocumentUri(context, uri) -> {
                    when {
                        // ExternalStorageProvider
                        isExternalStorageDocument(uri) -> {
                            val docId = DocumentsContract.getDocumentId(uri)
                            val split = docId.split(":").toTypedArray()
                            val type = split[0]
                            // This is for checking Main Memory
                            return if ("primary".equals(type, ignoreCase = true)) {
                                if (split.size > 1) {
                                    Environment.getExternalStorageDirectory()
                                        .toString() + "/" + split[1]
                                } else {
                                    Environment.getExternalStorageDirectory().toString() + "/"
                                }
                                // This is for checking SD Card
                            } else {
                                "storage" + "/" + docId.replace(":", "/")
                            }
                        }
                        isDownloadsDocument(uri) -> {
                            val fileName = getFilePath(context, uri)
                            if (fileName != null) {
                                return Environment.getExternalStorageDirectory()
                                    .toString() + "/Download/" + fileName
                            }
                            var id = DocumentsContract.getDocumentId(uri)
                            if (id.startsWith("raw:")) {
                                id = id.replaceFirst("raw:".toRegex(), "")
                                val file = File(id)
                                if (file.exists()) return id
                            }
                            val contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"),
                                java.lang.Long.valueOf(id)
                            )
                            return getDataColumn(context, contentUri, null, null)
                        }
                        isMediaDocument(uri) -> {
                            val docId = DocumentsContract.getDocumentId(uri)
                            val split = docId.split(":").toTypedArray()
                            val type = split[0]
                            var contentUri: Uri? = null
                            when (type) {
                                "image" -> {
                                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                }
                                "video" -> {
                                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                                }
                                "audio" -> {
                                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                                }
                            }
                            val selection = "_id=?"
                            val selectionArgs = arrayOf(split[1])
                            return getDataColumn(context, contentUri, selection, selectionArgs)
                        }
                    }
                }
                "content".equals(uri.scheme, ignoreCase = true) -> {
                    // Return the remote address
                    return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                        context,
                        uri,
                        null,
                        null
                    )
                }
                "file".equals(uri.scheme, ignoreCase = true) -> {
                    return uri.path
                }
            }
            return null
        }

        fun getDataColumn(
            context: Context, uri: Uri?, selection: String?,
            selectionArgs: Array<String>?
        ): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(
                column
            )
            try {
                if (uri == null) return null
                cursor = context.contentResolver.query(
                    uri, projection, selection, selectionArgs,
                    null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }


        fun getFilePath(context: Context, uri: Uri?): String? {
            var cursor: Cursor? = null
            val projection = arrayOf(
                MediaStore.MediaColumns.DISPLAY_NAME
            )
            try {
                if (uri == null) return null
                cursor = context.contentResolver.query(
                    uri, projection, null, null,
                    null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                    return cursor.getString(index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri.authority
        }

         fun makeFileCopyInCacheDir(contentUri :Uri , ctx : Context) : String? {
            try {
                val filePathColumn = arrayOf(
                    //Base File
                    MediaStore.Files.FileColumns._ID,
                    MediaStore.Files.FileColumns.TITLE,
                    MediaStore.Files.FileColumns.DATA,
                    MediaStore.Files.FileColumns.SIZE,
                    MediaStore.Files.FileColumns.DATE_ADDED,
                    MediaStore.Files.FileColumns.DISPLAY_NAME,
                    //Normal File
                    MediaStore.MediaColumns.DATA,
                    MediaStore.MediaColumns.MIME_TYPE,
                    MediaStore.MediaColumns.DISPLAY_NAME
                )
                //val contentUri = FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.provider", File(mediaUrl))
                val contentResolver = ctx.contentResolver
                val returnCursor = contentUri.let { contentResolver.query(it, filePathColumn, null, null, null) }
                if (returnCursor!=null) {
                    returnCursor.moveToFirst()
                    val nameIndex = returnCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                    val name = returnCursor.getString(nameIndex)
                    val file = File( ctx.cacheDir, name)
                    val inputStream = contentResolver.openInputStream(contentUri)
                    val outputStream = FileOutputStream(file)
                    var read = 0
                    val maxBufferSize = 1 * 1024 * 1024
                    val bytesAvailable = inputStream!!.available()

                    //int bufferSize = 1024;
                    val bufferSize = Math.min(bytesAvailable, maxBufferSize)
                    val buffers = ByteArray(bufferSize)
                    while (inputStream.read(buffers).also { read = it } != -1) {
                        outputStream.write(buffers, 0, read)
                    }
                    inputStream.close()
                    outputStream.close()
                    Log.e("File Path", "Path " + file.path)
                    Log.e("File Size", "Size " + file.length())
                    return file.absolutePath
                }
            } catch (ex: Exception) {
                Log.e("Exception", ex.message!!)
            }
            return contentUri.let { getRealPathFromURI(ctx, it).toString() }
        }

    }
}

