package com.srabbijan.common.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.makePhoneCall(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    if (intent.resolveActivity(this.packageManager) != null) {
        this.startActivity(intent)
    }
}
fun Context.openMessagingApp(phoneNumber: String, message: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("sms:$phoneNumber")
        putExtra("sms_body", message)
    }
    if (intent.resolveActivity(this.packageManager) != null) {
        this.startActivity(intent)
    }
}