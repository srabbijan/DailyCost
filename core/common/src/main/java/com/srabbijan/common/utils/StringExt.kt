package com.srabbijan.common.utils

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast

//------------------------------------------
// Log
//------------------------------------------
const val APP_TAG = "RABBI"

fun String.logVerbose(tag: String = APP_TAG) {
//    if (buildType.DEBUG)
        Log.v(tag, this)
}

fun String.logDebug(tag: String = APP_TAG) {
//    if (buildType.DEBUG)
        Log.d(tag, this)
}

fun String.logInfo(tag: String = APP_TAG) {
//    if (buildType.DEBUG)
        Log.i(tag, this)
}

fun String.logWarn(tag: String = APP_TAG) {
//    if (buildType.DEBUG)
        Log.w(tag, this)

}

fun String.logError(tag: String = APP_TAG) {
//      if (buildType.DEBUG)
    Log.e(tag, this)
}

//@Composable
//fun String.ShowToast() {
//    Toast.makeText(LocalContext.current, this, Toast.LENGTH_SHORT).show()
//}
fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}
// this fun use for check if value is number
fun isNumber(value: String): Boolean {
    return value.isEmpty() || Regex("^\\d+\$").matches(value)
}

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean {
    return password.any { it.isDigit() } &&
            password.any { it.isLetter() }
}

val String.banglaNumber: String
    get() {
        var banglaNumber = ""

        val charArray = this.toCharArray()
        for (char in charArray) {
            when (char) {
                '0' -> {
                    banglaNumber += "০"
                }

                '1' -> {
                    banglaNumber += "১"
                }

                '2' -> {
                    banglaNumber += "২"
                }

                '3' -> {
                    banglaNumber += "৩"
                }

                '4' -> {
                    banglaNumber += "৪"
                }

                '5' -> {
                    banglaNumber += "৫"
                }

                '6' -> {
                    banglaNumber += "৬"
                }

                '7' -> {
                    banglaNumber += "৭"
                }

                '8' -> {
                    banglaNumber += "৮"
                }

                '9' -> {
                    banglaNumber += "৯"
                }

                else -> {
                    banglaNumber += char.toString()
                }
            }
        }

        return banglaNumber
    }