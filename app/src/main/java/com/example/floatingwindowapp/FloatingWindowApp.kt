package com.example.floatingwindowapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebView
import android.widget.Button

class FloatingWindowApp : Service() {
    private lateinit var floatView: ViewGroup
    private lateinit var floatWindowLayoutParams: WindowManager.LayoutParams
    private var LAYOUT_TYPE: Int? = null
    private lateinit var windowManager: WindowManager
    private lateinit var webDes: WebView
    private lateinit var btnMax: Button
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate(){
        super.onCreate()
    }
}