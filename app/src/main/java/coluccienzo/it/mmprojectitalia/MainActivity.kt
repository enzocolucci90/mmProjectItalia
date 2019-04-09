package coluccienzo.it.mmprojectitalia

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Bitmap
import android.util.Log
import android.view.KeyEvent
import android.app.ProgressDialog




class MainActivity : AppCompatActivity() {
    private val URL : String = "URL"
    var url : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){
            url = "https://www.mmprojectitalia.it/"
        }
        setContentView(R.layout.activity_main)
        val dialog = ProgressDialog.show(
            this, "",
            "Loading. Please wait...", true
        )
        webview.loadUrl(url)

        webview.settings?.javaScriptEnabled = true

        webview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                webview.loadUrl(url)
                return true
            }

            /*override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                    dialog.show()

                }*/

                override fun onPageFinished(view: WebView, url: String) {
                    dialog.hide()
                }

            /*override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                Log.e("Error", description)
            }*/

        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }
}

