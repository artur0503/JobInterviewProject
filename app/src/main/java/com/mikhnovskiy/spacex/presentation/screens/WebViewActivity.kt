package com.mikhnovskiy.spacex.presentation.screens

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.navArgs
import com.mikhnovskiy.spacex.core.presentation.BaseActivity
import com.mikhnovskiy.spacex.databinding.ActivityWebViewBinding
import java.util.*

class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {

    private val args : WebViewActivityArgs by navArgs()

    override fun bind() = ActivityWebViewBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWebView()
        binder.webView.loadUrl(args.url)
    }

    @SuppressLint("WebViewClientOnReceivedSslError")
    private fun setupWebView() {
        binder.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                hideProgress()
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                val currentDate = Date(System.currentTimeMillis())
                val certEndDate: Date = error!!.certificate.validNotAfterDate
                if (currentDate.before(certEndDate)) {
                    handler?.proceed()
                } else {
                    view?.stopLoading()
                    handler?.cancel()
                }
            }
        }
    }
}