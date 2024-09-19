package com.srabbijan.dashboard.presentation.report

import android.content.Context
import android.content.Intent
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.print.PrintAttributes
import android.print.PrintManager
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
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
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.srabbijan.common.utils.logDebug
import com.srabbijan.design.AppToolbarWithBack
import com.srabbijan.design.R
import java.io.File
import java.io.FileOutputStream

@Composable
fun HtmlToPdfScreen(
    htmlContent: String,
    navHostController: NavHostController
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppToolbarWithBack(
                "Report",
                actions = {
                    IconButton(
                        onClick = {
                            "loading".logDebug()
                             WebView(context).apply {
                                settings.javaScriptEnabled = true
                                webViewClient = object : WebViewClient() {
                                    override fun onPageFinished(view: WebView?, url: String?) {
                                        createWebViewPdf(context, this@apply)
                                    }
                                }
                                loadDataWithBaseURL(null, htmlContent, "text/HTML", "UTF-8", null)
                            }
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

fun generatePdfAndSaveToStorage(context: Context, webView: View) {
    val width: Int = context.resources.displayMetrics.widthPixels
    val height: Int = context.resources.displayMetrics.heightPixels
    webView.measure(
        View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
    )
    webView.layout(0, 0, width, height)

    // Create a PdfDocument
    val pdfDocument = PdfDocument()

    // Define a page info with the desired page size (A4 size here)
    val pageInfo = PdfDocument.PageInfo.Builder(webView.width, webView.height, 1).create()

    // Start a page
    val page = pdfDocument.startPage(pageInfo)

    // Render the WebView content into the PDF page
    webView.draw(page.canvas)

    // Finish the page
    pdfDocument.finishPage(page)

    // Create a file to save the PDF

    val dir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        //Android 11 and above
        File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Hishabee")
    } else {
        //Android 10 and below
        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
    }

    if (!dir?.exists()!!) {
        dir.mkdir()
    }
    //generateFileName("srm")
    val file = File(dir, "REPORT_LIST_${System.currentTimeMillis()}" + ".pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        openPdf(context, file)

    } catch (e: Exception) {
        e.printStackTrace()
    }
    pdfDocument.close()
}
fun openPdf(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(context, "com.srabbijan.dailycost.xmlToPdf.provider", file)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(
            context, "Error opening PDF: ${e.message}", Toast.LENGTH_SHORT
        ).show()
    }
}