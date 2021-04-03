package com.dss.hrms.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dss.hrms.R
import com.dss.hrms.databinding.ActivityWebViewBinding
import com.namaztime.namaztime.database.MySharedPreparence
import javax.inject.Inject


class WebViewActivity : BaseActivity() {

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
            Log.e("url","url : ${url}")
        }
        binding?.swipeRefreshL?.isRefreshing = true


        loadUr()

        binding?.backBtnCV?.setOnClickListener {

            onBackPressed()
        }
        binding?.swipeRefreshL?.setOnRefreshListener { loadUr() }

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