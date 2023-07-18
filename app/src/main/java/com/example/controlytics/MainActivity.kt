package com.controlytics.controlytics_app

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewAssetLoader
import com.controlytics.controlytics_app.databinding.ActivityMainBinding



// Import necessary libraries

// Declare the binding for the main activity layout
private lateinit var binding: ActivityMainBinding

// Suppress lint warning for setting JavaScript enabled in the WebView
@SuppressLint("SetJavaScriptEnabled")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the WebView and its settings
        val webView = binding.webView
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            cacheMode = WebSettings.LOAD_DEFAULT
            allowFileAccess = true
            allowContentAccess = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        // Set up WebViewAssetLoader for loading local assets
        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(this))
            .build()

        // Set the WebViewClient to handle resource requests
        webView.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView,
                request: String
            ): WebResourceResponse? {
                // Intercept the resource request and load from local assets if necessary
                val uri = Uri.parse(request)
                return assetLoader.shouldInterceptRequest(uri)
            }
        }

        val url = "https://app.controlytics.ai/login" // Replace with your desired website URL
        webView.loadUrl(url)


    }
}


