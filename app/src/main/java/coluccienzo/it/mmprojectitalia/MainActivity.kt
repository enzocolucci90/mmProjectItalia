package coluccienzo.it.mmprojectitalia

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {
    private val URL: String = "URL"

    var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            url = "https://www.mmprojectitalia.it/"
        }
        setContentView(R.layout.activity_main)

        setToolbar(toolbar)

        val dialog = ProgressDialog.show(
            this, "",
            "Loading. Please wait...", true
        )

        if (url != "") {
            webview.loadUrl(url)
        }

        webview.settings?.javaScriptEnabled = true

        webview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url?.contains("mailto:")) {
                    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                    startActivity(Intent.createChooser(emailIntent, "Chooser Title"))
                } else if (url?.equals("tel:+390828722614")){
                    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                    startActivity(Intent.createChooser(callIntent, "Chooser Title"))
                }else if (url?.contains("https://maps.google.com")){
                    val mapsIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    mapsIntent.setPackage("com.google.android.apps.maps");
                    startActivity(Intent.createChooser(mapsIntent, "Chooser Title"))
                } else {
                    webview.loadUrl(url)
                }
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

    private fun setToolbar(toolbar: View?) {
        left_text_view.setOnClickListener {
            webview.goBack()
        }
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString(URL, this.url)
        // etc.
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        url = savedInstanceState.getString(URL)

        webview.loadUrl(url)
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

