package com.srabbijan.common.utils


import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
const val styleText = "    <style>\n" +
        "        body {\n" +
        "            margin: 1;\n" +
        "            padding: 0;\n" +
        "            font-family: 'PT Sans', sans-serif;\n" +
        "        }\n" +
        "        @page {\n" +
        "            size: 2.8in 11in;\n" +
        "            margin-top: 0cm;\n" +
        "            margin-left: 0cm;\n" +
        "            margin-right: 0cm;\n" +
        "        }\n" +
        "        table {\n" +
        "            width: 100%;\n" +
        "        }\n" +
        "        tr {\n" +
        "            width: 100%;\n" +
        "        }\n" +
        "        h1 {\n" +
        "            text-align: center;\n" +
        "            vertical-align: middle;\n" +
        "        }\n" +
        "        #logo {\n" +
        "            width: 60%;\n" +
        "            text-align: center;\n" +
        "            -webkit-align-content: center;\n" +
        "            align-content: center;\n" +
        "            padding: 5px;\n" +
        "            margin: 2px;\n" +
        "            display: block;\n" +
        "            margin: 0 auto;\n" +
        "        }\n" +
        "        header {\n" +
        "            width: 100%;\n" +
        "            text-align: center;\n" +
        "            -webkit-align-content: center;\n" +
        "            align-content: center;\n" +
        "            vertical-align: middle;\n" +
        "        }\n" +
        "        .items thead {\n" +
        "            text-align: center;\n" +
        "        }\n" +
        "        .center-align {\n" +
        "            text-align: center;\n" +
        "        }\n" +
        "        .bill-details td {\n" +
        "            font-size: 12px;\n" +
        "        }\n" +
        "        .receipt {\n" +
        "            font-size: medium;\n" +
        "        }\n" +
        "        .items .heading {\n" +
        "            font-size: 12.5px;\n" +
        "            text-transform: uppercase;\n" +
        "            border-top:1px solid black;\n" +
        "            margin-bottom: 4px;\n" +
        "            border-bottom: 1px solid black;\n" +
        "            vertical-align: middle;\n" +
        "        }\n" +
        "        .items thead tr th:first-child,\n" +
        "        .items tbody tr td:first-child {\n" +
        "            width: 47%;\n" +
        "            min-width: 47%;\n" +
        "            max-width: 47%;\n" +
        "            word-break: break-all;\n" +
        "            text-align: left;\n" +
        "        }\n" +
        "        .items td {\n" +
        "            font-size: 12px;\n" +
        "            text-align: right;\n" +
        "            vertical-align: bottom;\n" +
        "        }\n" +
        "        .price::before {\n" +
        "             content: \"\\09F3\";\n" +
        "            font-family: Arial;\n" +
        "            text-align: right;\n" +
        "        }\n" +
        "        .sum-up {\n" +
        "            text-align: right !important;\n" +
        "        }\n" +
        "        .total {\n" +
        "            font-size: 13px;\n" +
        "            border-top:1px dashed black !important;\n" +
        "            border-bottom:1px dashed black !important;\n" +
        "        }\n" +
        "        .total.text, .total.price {\n" +
        "            text-align: right;\n" +
        "        }\n" +
        "        .total.price::before {\n" +
        "            content: \"\\09F3\"; \n" +
        "        }\n" +
        "        .line {\n" +
        "            border-top:1px solid black !important;\n" +
        "        }\n" +
        "        .heading.rate {\n" +
        "            width: 20%;\n" +
        "             text-align: right;\n" +
        "        }\n" +
        "        .heading.amount {\n" +
        "            width: 25%;\n" +
        "             text-align: right;\n" +
        "        }\n" +
        "        .heading.qty {\n" +
        "            width: 5%\n" +
        "        }\n" +
        "        p {\n" +
        "            padding: 1px;\n" +
        "            margin: 0;\n" +
        "        }\n" +
        "        section, footer {\n" +
        "            font-size: 12px;\n" +
        "        }\n" +
        "    </style>\n"

const val itemHeader = "<thead>\n" +
        "            <tr>\n" +
        "                <th class=\"heading name\">Item</th>\n" +
        "                <th class=\"heading rate\">Type</th>\n" +
        "                <th class=\"heading qty\">Qty</th>\n" +
        "                <th class=\"heading amount\">Amount</th>\n" +
        "            </tr>\n" +
        "        </thead>"

const val footer = "    <footer style=\"text-align:center\">\n" +
        "        <p>Powered By @srabbijan</p>\n" +
        "    </footer>"


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

data class PdfGeneration(
    val name: String,
    val type: String,
    val qty: String,
    val amount: String,
)
data class PdfGenerationModel(
    val items: List<PdfGeneration>,
    val transaction: String,
    val cashIn: String,
    val cashOut: String,
    val balance: String,
    val pdfTitle: String
)
fun pdfGenerate( data: PdfGenerationModel): String {
    return "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            styleText +
            "</head>\n" +
            "<body>\n" +
            getHeaderText(data.pdfTitle)+
            "<table class=\"items\">\n" +
            itemHeader +
            "<tbody>\n" +
            itemBody(data.items) +
            itemSummation(
                data.transaction,
                data.cashIn,
                data.cashOut,
                data.balance
            )+
            "</tbody>"+
            "</table>\n" +
            footer +
            "</body>\n" +
            "</html>\n"
}

fun getHeaderText(month: String)   =
        "    <table class=\"bill-details\">\n" +
        "        <tbody>\n" +
        "            <tr>\n" +
        "                <th class=\"center-align\" colspan=\"2\"><span class=\"receipt\">$month</span></th>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td>Report Generate Date <span><br>${getCurrentDateTime()}</span></td>\n" +
        "            </tr>\n" +
        "        </tbody>\n" +
        "    </table>"

fun itemBody(items: List<PdfGeneration>) :String{
    var stringBuilder = ""
    items.forEach {
        stringBuilder += "            <tr>\n" +
                "                <td>${it.name}</td>\n" +
                "                <td>${it.type}</td>\n" +
                "                <td>${it.qty}</td>\n" +
                "                <td class=\"price\">${it.amount}</td>\n" +
                "            </tr>\n"
    }
    return stringBuilder
}

fun itemSummation(
    transaction: String,
    cashIn: String ,
    cashOut: String ,
    balance: String
) = "  <tr>\n" +
        "                <td colspan=\"3\" class=\"sum-up line\">Total transaction</td>\n" +
        "                <td class=\"line price\">$transaction</td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td colspan=\"3\" class=\"sum-up\">Cash In</td>\n" +
        "                <td class=\"price\">$cashIn</td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <td colspan=\"3\" class=\"sum-up\">Cash Out</td>\n" +
        "                <td class=\"price\">$cashOut</td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "                <th colspan=\"3\" class=\"total text\">Balance</th>\n" +
        "                <th class=\"total price\">$balance</th>\n" +
        "            </tr>"