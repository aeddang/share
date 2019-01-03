package com.ironleft.sms.smsmanager

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import com.ironleft.sms.smsmanager.MainActivity
import com.ironleft.sms.smsmanager.PageCertification
import com.ironleft.sms.smsmanager.R
import lib.core.ActivityCore
import lib.core.PageObject
import lib.core.PopupCore


class PopupWebView(context: Context, pageInfo: PageObject) : PopupCore(context,pageInfo)
{
    var webView: WebView? = null
    var authPath = ""
    var landingPath = ""
    init {
        View.inflate(context, R.layout.popup_webview, this)
        pageInfo.isHome = true
        body = findViewById<View>(R.id._body) as FrameLayout
        webView = findViewById<View>(R.id._webView) as WebView

        webView?.setWebViewClient(CustomWebViewClientPopup()) // 응룡프로그램에서 직접 url 처리
        val set = webView?.getSettings()
        set?.javaScriptEnabled = true
        set?.builtInZoomControls = false
        webView?.visibility = View.GONE
    }

    override fun doMovedInit() {
        super.doMovedInit()

        var mc = ActivityCore.shareInstence() as MainActivity?
        if(mc == null) return
        landingPath = Config.API_HOST + Config.WEB_SETTING
        authPath = Config.API_HOST + Config.WEB_AUTH + "?auth_number=" + mc.getCertificationKey()
        loadWebview(authPath)
    }

    private fun loadWebview(path:String)
    {
        Log.i("PATH",path)
        webView?.loadUrl(path)
    }

    override fun doRemove()
    {
        super.doRemove()
        webView?.setWebViewClient(null)
        webView?.stopLoading()
        webView?.destroy()
        webView = null
    }

    internal inner class CustomWebViewClientPopup : WebViewClient()
    {

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {

            super.onPageStarted(view, url, favicon)
            ActivityCore.shareInstence()?.loadingStart(false)
        }

        override fun onPageFinished(view: WebView, url: String) {

            super.onPageFinished(view, url)
            ActivityCore.shareInstence()?.loadingStop()

            if(url == authPath)
            {
                loadWebview(landingPath)
            }
            if(url == landingPath) webView?.visibility = View.VISIBLE

        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError)
        {
            //mainActivity?.loadingStop()
            //Toast.makeText(mainActivity,R.string.msg_network_err_kor, Toast.LENGTH_SHORT).show()
            super.onReceivedError(view, request, error)
        }

        override fun onReceivedHttpError(view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse)
        {
            //mainActivity?.loadingStop()
            //Toast.makeText(mainActivity,R.string.msg_network_err_kor, Toast.LENGTH_SHORT).show()
            super.onReceivedHttpError(view,request,errorResponse)
        }




    }






}