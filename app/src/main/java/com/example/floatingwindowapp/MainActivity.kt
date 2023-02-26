package com.example.floatingwindowapp

import android.app.ActivityManager
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var dialog: AlertDialog
    private lateinit var btnMin: Button
    private lateinit var webDes: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.settings.javaScriptEnabled = true
//        myWebView.settings.userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/110.0"
        myWebView.webViewClient = WebViewClient()
        myWebView.loadUrl("https://chat.openai.com")

        btnMin = findViewById(R.id.btnMin)
        webDes = findViewById(R.id.webview)

        if(isServiceRunning()){
            stopService(Intent(this@MainActivity, FloatingWindowApp::class.java))

        }

        btnMin.setOnClickListener(){
            if (checkOverlayPermission()){
                startService(Intent(this@MainActivity, FloatingWindowApp::class.java))
                finish()
            } else {
                requestFloatingWindowPermission()
            }
        }

    }

    private fun isServiceRunning(): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for( service in manager.getRunningServices(Int.MAX_VALUE)){
            if(FloatingWindowApp::class.java.name == service.service.className){
                return true
            }
        }
        return false
    }

    private fun requestFloatingWindowPermission(){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle("Screen Overlay Permission")
        builder.setMessage("Enable 'Display over other apps' ")
        builder.setPositiveButton("Open Settings", DialogInterface.OnClickListener{dialogInterface, i ->
            val intent = Intent (
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
                    )
            startActivityForResult(intent, RESULT_OK)
        })

        dialog = builder.create()
        dialog.show()
    }

    private fun checkOverlayPermission(): Boolean {
        return if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            Settings.canDrawOverlays(this)
        } else {
            return true
        }
    }
}