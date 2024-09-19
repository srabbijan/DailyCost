package com.srabbijan.dashboard.presentation.report

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.srabbijan.common.utils.logDebug
import com.srabbijan.design.AppToolbarWithBack
import com.srabbijan.design.R

@Composable
fun HtmlToPdfScreen(
    htmlContent: String,
    navHostController: NavHostController
) {
    val context = LocalContext.current


    Scaffold(
        topBar = {
            AppToolbarWithBack(
                "Export",
                actions = {
                    IconButton(
                        onClick = {
                            "loading".logDebug()
                             createWebViewPdf(context, WebView(context).apply {
                                settings.javaScriptEnabled = true
                                loadDataWithBaseURL(null, htmlContent, "text/HTML", "UTF-8", null)
                            })
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Print,
                            contentDescription = null
                        )
                    }
                }
            ) {
                navHostController.navigateUp()
            }
        }
    ) { paddingValues ->
        Column (modifier = Modifier.padding(paddingValues)){
            AndroidView(factory = {
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    loadDataWithBaseURL(null, htmlContent, "text/HTML", "UTF-8", null)
                }
            }, update = {})
        }
    }
}

fun createWebViewPdf(context: Context, webView: WebView) {
    "success".logDebug()
    // Create a PrintManager instance
    val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager

    // Set the job name, which will be the name of the PDF document
    val jobName = "${context.getString(R.string.product_name)} Document"

    // Create a PrintDocumentAdapter
    val printAdapter = webView.createPrintDocumentAdapter(jobName)

    // Set up PrintAttributes to specify paper size and resolution
    val printAttributes = PrintAttributes.Builder()
        .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
        .setResolution(PrintAttributes.Resolution("pdf", "pdf", 300, 300))
        .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
        .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
        .build()

    // Print the document
    printManager.print(jobName, printAdapter, printAttributes)
}