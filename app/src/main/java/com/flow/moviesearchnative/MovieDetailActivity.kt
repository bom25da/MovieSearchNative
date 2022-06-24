package com.flow.moviesearchnative

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.flow.moviesearchnative.databinding.MovieDetailBinding
import com.flow.moviesearchnative.databinding.MovieSearchListBinding

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var datas : String
    private lateinit var binding: MovieDetailBinding
    private lateinit var mContainer : FrameLayout
    private lateinit var mWebView : WebView
    private lateinit var mWebViewPop : WebView
    private lateinit var mWebSettings : WebSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=MovieDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        datas = intent.getSerializableExtra("data") as String


        val webView = binding.webView
        //webView.webViewClient = WebViewClient()
        webView.loadUrl(datas)
    }
}