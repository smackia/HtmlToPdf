package com.lyxel.smaplexyz

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.print.PdfPrint
import android.print.PrintAttributes
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val myWebView = WebView(this)
//        setContentView(myWebView)
        setContentView(R.layout.activity_main)
        Log.i("PPP", "Activity OnCreate ")
//        val manager: FragmentManager = supportFragmentManager
//        val transaction = manager.beginTransaction()
//        transaction.replace(R.id.container, TestFragment(), "test")
//        transaction.addToBackStack(null)
//        transaction.commit()
        createDocument()
    }

    private var mWebView: WebView? = null

    private fun createDocument() {
        val view = WebView(this)
        view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                createWebPrintJob(view, 2210204)
                mWebView = null
            }
        }
        view.loadUrl("https://www.google.com")
        mWebView = view
    }


    fun getFile(name: String, activity: Activity): File {
        val extr = activity.getExternalFilesDir("")
        val fileName = name
        return File(extr, fileName)
    }


    private fun createWebPrintJob(webView: WebView, date: Long) {
        try {
            val jobName = getString(R.string.app_name) + " Document"
            val attributes = PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.NA_LEGAL)
                .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build()
            val path =getExternalFilesDir("")

            val pdfPrint = PdfPrint(attributes)
            pdfPrint.print(
                webView.createPrintDocumentAdapter(jobName),
                path!!,
                "output_$date.pdf"
            )
            sharePDF(getFile("output_$date.pdf",this))
            Log.i("pdf", "pdf created")
        } catch (e: Exception) {
            Log.e("pdf", " pdf failed e.localizedMessage")
        }
    }


    private fun sharePDF(file: File) {
        //  val file = File(pdfFilePath)
//        val uri = Uri.fromFile(file)
        val URI = FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID + ".provider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_STREAM, URI)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivity(Intent.createChooser(intent, "Share PDF"))
    }


//    private fun createWebPrintJob(webView: WebView) {
//        (this.getSystemService(PRINT_SERVICE) as? PrintManager)?.let { printManager ->
//            val jobName = "${getString(R.string.app_name)} Document"
//            val printAdapter = webView.createPrintDocumentAdapter(jobName)
//            printManager.print(
//                jobName,
//                printAdapter,
//                PrintAttributes.Builder().build()
//            )
//        }
//    }


    override fun onResume() {
        Log.i("PPP", "Activity OnResume ")
        super.onResume()
    }

    override fun onRestart() {
        Log.i("PPP", "Activity OnRestart ")
        super.onRestart()
    }

    override fun onPause() {
        Log.i("PPP", "Activity OnPause ")
        super.onPause()
    }

    override fun onStop() {
        Log.i("PPP", "Activity OnStop ")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i("PPP", "Activity OnDestroy ")
        super.onDestroy()
    }

    override fun onStart() {
        Log.i("PPP", "Activity OnStart ")
        super.onStart()
    }

}