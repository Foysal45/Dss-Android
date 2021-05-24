package com.dss.hrms.view.activity

import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.dss.hrms.R
import com.dss.hrms.databinding.ActivityWebViewBinding
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject


class WebViewActivity : BaseActivity() {
    private var afterLolipop: ValueCallback<Array<Uri?>>? = null
    private var mUploadMessage: ValueCallback<Uri?>? = null
    var binding: ActivityWebViewBinding? = null

    private var url = ""

    @Inject
    lateinit var preparence: MySharedPreparence


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        // changeStatusBarIconColor()

        if (intent.extras != null) {
            url = intent.getStringExtra("url")!!
            Log.e("url", "url : ${url}")
        }
        binding?.swipeRefreshL?.isRefreshing = true


        loadUr()

        binding?.backBtnCV?.setOnClickListener {

            onBackPressed()
        }
        binding?.swipeRefreshL?.setOnRefreshListener { loadUr() }


        binding?.webViewId?.setWebChromeClient(object : WebChromeClient() {
            // For Android 3.0+ - undocumented method
            fun openFileChooser(uploadMsg: ValueCallback<Uri?>, acceptType: String?) {
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(Intent.createChooser(i, "File Chooser"), 101)
                Log.i("DEBUG", "Open file Chooser")
                mUploadMessage = uploadMsg
            }

            // For Android > 4.1 - undocumented method
            fun openFileChooser(
                uploadMsg: ValueCallback<Uri?>,
                acceptType: String?,
                capture: String?
            ) {
                mUploadMessage = uploadMsg
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(pickPhoto, 101)
            }

            // For Android > 5.0
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                startActivityForResult(fileChooserParams.createIntent(), 101)

                return false
            }
        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            101 -> if (resultCode == RESULT_OK) {
                val result = if (intent == null || resultCode != RESULT_OK) null else intent.data
                if (mUploadMessage != null) {
                    mUploadMessage!!.onReceiveValue(result)
                } else if (afterLolipop != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        afterLolipop?.onReceiveValue(
                            WebChromeClient.FileChooserParams.parseResult(
                                resultCode,
                                intent
                            )
                        )
                        afterLolipop = null
                    }
                }
                mUploadMessage = null
            }
        }
    }


    override fun onStart() {
        super.onStart()

        binding?.swipeRefreshL?.viewTreeObserver?.addOnScrollChangedListener {
            if (binding?.webViewId?.getScrollY() == 0)
                binding?.swipeRefreshL?.setEnabled(true)
            else
                binding?.swipeRefreshL?.setEnabled(false)

        }
    }

    private val mOnScrollChangedListener: OnScrollChangedListener? = null
    override fun onStop() {
        binding!!.swipeRefreshL.viewTreeObserver.removeOnScrollChangedListener(
            mOnScrollChangedListener
        )
        super.onStop()
    }

    private fun loadUr() {
        binding!!.webViewId.loadUrl(url)
        binding!!.webViewId.settings.javaScriptEnabled = true
        binding!!.webViewId.settings.displayZoomControls = true
        binding!!.webViewId.settings.loadsImagesAutomatically = true
        binding!!.webViewId.settings.setDomStorageEnabled(true);
        binding!!.webViewId.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        binding!!.webViewId.webViewClient = MyWebViewClient(binding)
    }

    class MyWebViewClient constructor(binding: ActivityWebViewBinding?) : WebViewClient() {
        var binding: ActivityWebViewBinding? = null

        init {
            this.binding = binding
        }

        override fun onPageFinished(view: WebView, url: String) {
            binding?.swipeRefreshL?.setRefreshing(false)
        }
    }

    override fun onBackPressed() {
        if (binding!!.webViewId.canGoBack()) {
            binding!!.webViewId.goBack()
        } else {
            super.onBackPressed()
        }
    }
}