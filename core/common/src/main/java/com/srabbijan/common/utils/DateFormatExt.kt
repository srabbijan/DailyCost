package com.srabbijan.common.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val FORMAT_dd_MMM_yyyy_hh_mm_ss_aaa = "dd MMMM yyyy hh:mm:ss aaa"
const val FORMAT_yyyy_MM_dd = "yyyy-MM-dd"
const val FORMAT_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm"
const val FORMAT_dd_MM_yyyy_with_hifen = "dd-MM-yyyy"
const val FORMAT_dd_MM_yyyy_with_bar = "dd/MM/yyyy"
const val FORMAT_dd_MMM = "dd MMM"
const val FORMAT_dd_MMMM_yyyy = "dd MMMM yyyy"
const val FORMAT_dd_MMMM_C_yyyy = "dd MMMM, yyyy"
const val FORMAT_dd_MMM_yyyy = "dd MMM yyyy"
const val FORMAT_dd_MMM_yyyy_hh_mm_aaa = "dd MMM yyyy | hh:mm aaa"
const val FORMAT_hh_mm_aaa = "hh:mm aaa" // 12hr format
const val FORMAT_kk_mm = "kk:mm" // 24hr format
const val FORMAT_MMM_yyyy = "MMM yyyy"
const val FORMAT_MMMM_yyyy = "MMMM yyyy"
const val FORMAT_EEE_dd_MMM = "EEE, dd MMM"
const val FORMAT_yyyy_MM_dd_T_HH_mm_ss_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val FORMAT_yyyy_MM_dd_T_HH_mm_ss_SSSXXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
const val FORMAT_yyyy_MM_dd_T_HH_mm_ss = "yyyy-MM-dd'T'HH:mm:ss"
const val FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss"
const val FORMAT_MMM_dd_yyyy = "MMM dd yyyy"
const val FORMAT_HH_mm = "HH:mm"
fun String.format(
    inFormat: String,
    outFormat: String,
    destLocale: Locale? = Locale.getDefault()
): String {
    return try {
        val inFormatter = SimpleDateFormat(inFormat, Locale.ENGLISH)
        val outFormatter = SimpleDateFormat(outFormat, destLocale)
        outFormatter.format(inFormatter.parse(this)!!)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String.formatUtcTime(
    outFormat: String,
    destLocale: Locale? = Locale.getDefault()
): String? {
    if (this.isEmpty()) return null
    return format(
        inFormat = FORMAT_yyyy_MM_dd_T_HH_mm_ss_SSSXXX,  // Available in API 24+
        outFormat = outFormat,
        destLocale = destLocale
    )
}

fun parseDateTimeWithFormat(value: Long?, format: String): String {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).format(Date(value!!))
    } catch (e: Exception) {
        ""
    }
}

fun parseDateTime(value: Long): String {
    val outputFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", Locale.getDefault())
    return try {
        val dateValue = Date(value)
        outputFormat.format(dateValue)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String.getDaysInterval(givenFormat: String = "yyyy-MM-dd"): String {
//    val givenFormat = "yyyy-MM-dd"
    val inputFormat = SimpleDateFormat(givenFormat, Locale.ENGLISH)

    return try {
        val dateValue: Date = inputFormat.parse(this)
        val currentDate = Date()
        val diff: Long = dateValue.time - currentDate.time
        TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun Long.convertToTime(outFormat: String? = null): String {
    return try {
        val date = Date(this)
        val format = SimpleDateFormat(outFormat ?: "dd/MM/yyyy hh:mm aa")
        format.format(date)
    } catch (e: Exception) {
        ""
    }
}

fun Long.convertToDate(): String {
    return try {
        val date = Date(this)
        val format = SimpleDateFormat("dd/MM/yyyy")
        format.format(date)
    } catch (e: Exception) {
        ""
    }
}




fun String?.compareDateWithNow(
    isBefore: Boolean = false,
    dateFormat: String = "dd MMMM yyyy hh:mm:ss a"
) =
    try {
        if (this == null) false
        else SimpleDateFormat(dateFormat, Locale.ENGLISH).parse(this)
            ?.let {
                if (isBefore) it.before(Date())
                else it.after(Date())
            } ?: false
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

fun String?.compareDateWithToday(
    isBefore: Boolean = false,
    dateFormat: String = "dd MMMM yyyy hh:mm:ss a"
) =
    try {
        if (this == null) false
        else SimpleDateFormat(FORMAT_dd_MMMM_yyyy, Locale.ENGLISH).parse(
            format(
                dateFormat,
                FORMAT_dd_MMMM_yyyy
            )
        )
            ?.let {
                val today = SimpleDateFormat(FORMAT_dd_MMMM_yyyy, Locale.ENGLISH).parse(
                    SimpleDateFormat(
                        FORMAT_dd_MMMM_yyyy,
                        Locale.ENGLISH
                    ).format(Date())
                )
                if (isBefore) it.before(today)
                else it.after(today)
            } ?: false
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

@SuppressLint("SimpleDateFormat")
fun getCurrentDateTime(format: String = FORMAT_yyyy_MM_dd_HH_mm_ss): String {
    val dateFormat = SimpleDateFormat(format, Locale("en"))
    val currentTime = Date()
    return dateFormat.format(currentTime)
}
fun getTodayFirstDateTime(): String {
    val dateFormat = SimpleDateFormat(FORMAT_yyyy_MM_dd, Locale("en"))
    val today = Date()
    return dateFormat.format(today) + " 00:00:00"
}
fun getTodayLastDateTime(): String {
    val dateFormat = SimpleDateFormat(FORMAT_yyyy_MM_dd, Locale("en"))
    val today = Date()
    return dateFormat.format(today) + " 23:59:59"
}
fun getThisMonthFirstDateTime():String{
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val monthFirstDay = calendar.time
    val df = SimpleDateFormat(FORMAT_yyyy_MM_dd, Locale("en"))
    return df.format(monthFirstDay)+ " 00:00:00"
}
@SuppressLint("SimpleDateFormat")
fun String.currentDateToUIFormat():String{
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
    val outPutFormat = SimpleDateFormat("dd MMM, yyyy")
    try {
        val inputDate = inputFormat.parse(this)
        val outPutDate = inputDate?.let { outPutFormat.format(it) }
        return outPutDate.toString()
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return " "
}
@SuppressLint("SimpleDateFormat")
fun Int.getNthDayBeforeDateTime(): String {
    val cal = Calendar.getInstance()
    val s = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
    cal.add(Calendar.DAY_OF_YEAR, -(this - 1))
    return s.format(Date(cal.timeInMillis))
}

fun String.toUiTime(inFormat: String = FORMAT_yyyy_MM_dd_HH_mm_ss, outFormat: String = FORMAT_dd_MMM_yyyy_hh_mm_aaa): String {
    val inputFormat = SimpleDateFormat(inFormat, Locale("en"))
    val outputFormat = SimpleDateFormat(outFormat, Locale("en"))
    val date = inputFormat.parse(this)
    return date?.let { outputFormat.format(it) }.toString()
}

@SuppressLint("SimpleDateFormat")
fun String.dateToTimestamp(): Long {
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
    val date = dateFormat.parse(this)
    val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
    val data1 = date?.let { formatter.format(it) }?.let { formatter.parse(it) }
    return data1!!.time
}

@SuppressLint("SimpleDateFormat")
fun String.convertApiDateToLocalDbDate(): String {
    val inputFormat = SimpleDateFormat("dd MMM,yyyy", Locale("en"))
    val outPutFormat = SimpleDateFormat(FORMAT_yyyy_MM_dd, Locale("en"))

    try {
        val inputDate = inputFormat.parse(this)
        val outPutDate = inputDate?.let { outPutFormat.format(it) }

        return outPutDate.toString()
    } catch (e: ParseException) {
       e.toString().logDebug("RABBI==")

    }
    return " "
}

@SuppressLint("SimpleDateFormat")
fun get7daysBeforeDate(): String? {
    val cal = Calendar.getInstance()
    val s = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
    cal.add(Calendar.DAY_OF_YEAR, -6)
    return s.format(Date(cal.timeInMillis))
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(format: String = "yyyy-MM-dd"): String {
    val cal = Calendar.getInstance()
    val s = SimpleDateFormat(format, Locale("en"))
    return s.format(Date(cal.timeInMillis))
}
@SuppressLint("SimpleDateFormat")
fun getTomorrowsDate(): String? {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 1)
    val tomorrow = calendar.time

    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd",Locale("en"))
    return dateFormat.format(tomorrow)
}
@SuppressLint("SimpleDateFormat")
fun getFirstDateOfMonth(format: String="yyyy-MM-dd"): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val monthFirstDay = calendar.time
    val df = SimpleDateFormat(format, Locale("en"))
    return df.format(monthFirstDay)
}
@SuppressLint("SimpleDateFormat")
fun getFirstDateTimeOfCurrentMonth(format: String= FORMAT_yyyy_MM_dd_HH_mm_ss): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val monthFirstDay = calendar.time
    val df = SimpleDateFormat(format, Locale("en"))
    return df.format(monthFirstDay)
}
@SuppressLint("SimpleDateFormat")
fun getFirstDateOfYear(): String {
    val calendar = Calendar.getInstance(Locale("en"))
    val year = calendar[Calendar.YEAR]
    return "$year-01-01"
}

@SuppressLint("SimpleDateFormat")
fun String.getCustomDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS",Locale("en"))
    val outPutFormat = SimpleDateFormat("dd MMM, yyyy hh:mm a",Locale("en"))

    try {
        val inputDate = inputFormat.parse(this)
        val outPutDate = inputDate?.let { outPutFormat.format(it) }
        return outPutDate.toString()
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return " "
}
@SuppressLint("SimpleDateFormat")
fun String.getCustomDate_dd_MM_YY(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS",Locale("en"))
    val outPutFormat = SimpleDateFormat("dd MMM, yy hh:mm a",Locale("en"))

    try {
        val inputDate = inputFormat.parse(this)
        val outPutDate = inputDate?.let { outPutFormat.format(it) }
        return outPutDate.toString()
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return " "
}

@SuppressLint("SimpleDateFormat")
fun String.getCustomDateAndTime( ): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale("en"))
    val outPutFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a",Locale("en"))
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    outPutFormat.timeZone = TimeZone.getTimeZone("UTC")
    try {
        val inputDate = inputFormat.parse(this)
        val outPutDate = inputDate?.let { outPutFormat.format(it) }
        return outPutDate.toString()
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return " "
}

@SuppressLint("SimpleDateFormat")
fun String.getCustomDateMonthDay( ): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale("en"))
    val outPutFormat = SimpleDateFormat("MMM dd",Locale("en"))
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    outPutFormat.timeZone = TimeZone.getTimeZone("UTC")
    try {
        val inputDate = inputFormat.parse(this)
        val outPutDate = inputDate?.let { outPutFormat.format(it) }
        return outPutDate.toString()
    } catch (e: ParseException) {
        println(e)
        e.printStackTrace()
    }
    return " "
}

@SuppressLint("SimpleDateFormat")
fun String.getCustomDateMonthDayYear( ): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale("en"))
    val outPutFormat = SimpleDateFormat("MMM dd, yyyy",Locale("en"))
    try {
        val inputDate = inputFormat.parse(this)
        val outPutDate = inputDate?.let { outPutFormat.format(it) }
        return outPutDate.toString()
    } catch (e: ParseException) {
        println(e)
        e.printStackTrace()
    }
    return " "
}

@SuppressLint("SimpleDateFormat")
fun String.getCustomDayMonthYear( ): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale("en"))
    val outPutFormat = SimpleDateFormat("dd MMM yyyy",Locale("en"))
    try {
        val inputDate = inputFormat.parse(this)
        val outPutDate = inputDate?.let { outPutFormat.format(it) }

        return outPutDate.toString()
    } catch (e: ParseException) {
        println(e)
        e.printStackTrace()
    }
    return " "
}

@SuppressLint("SimpleDateFormat")
fun String.getStringToTime(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale("en"))
    val outPutFormat = SimpleDateFormat("hh:mm",Locale("en"))

    try {
        val inputDate = inputFormat.parse(this)
        val outPutDate = inputDate?.let { outPutFormat.format(it) }
        return outPutDate.toString()
    } catch (e: ParseException) {
        println(e)
        e.printStackTrace()
    }
    return " "
}

@SuppressLint("SimpleDateFormat")
fun String.getStringToTimeAMPM(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale("en"))
    val outPutFormat = SimpleDateFormat("hh:mm a",Locale("en"))
    inputFormat.timeZone = TimeZone.getDefault()
    outPutFormat.timeZone = TimeZone.getDefault()
    try {
        val inputDate = inputFormat.parse(this)
        val outPutDate = inputDate?.let { outPutFormat.format(it) }
        return outPutDate.toString()
    } catch (e: ParseException) {
        println(e)
        e.printStackTrace()
    }
    return " "
}
@SuppressLint("SimpleDateFormat")
fun String.getOnlyDayFromDateString(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale("en"))
    val outPutFormat = SimpleDateFormat("dd",Locale("en"))
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    outPutFormat.timeZone = TimeZone.getTimeZone("UTC")
    try {
        val inputDate = inputFormat.parse(this)
        val outPutDate = inputDate?.let { outPutFormat.format(it) }
        Log.d("TAFF",outPutDate.toString())
        return outPutDate.toString()
    } catch (e: ParseException) {
        println(e)
        e.printStackTrace()
        Log.d("TAGG",e.toString())
    }
    return " "
}
@SuppressLint("SimpleDateFormat")
fun getCurrentYear(): Int {
    val calendar = Calendar.getInstance(Locale("en"))
    return calendar[Calendar.YEAR]
}
@SuppressLint("SimpleDateFormat")
fun getDayYearMonth(apiDate: String): Triple<Int, Int, Int> {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale("en"))
    val yF = SimpleDateFormat("yyyy",Locale("en"))
    val dF = SimpleDateFormat("dd",Locale("en"))
    val mF = SimpleDateFormat("MM",Locale("en"))

    try {
        val inputDate = inputFormat.parse(apiDate)
        val yD = inputDate?.let { yF.format(it).toInt() }
        val dD = inputDate?.let { dF.format(it).toInt() }
        val mD = inputDate?.let { mF.format(it).toInt() }
        return Triple(dD!!, mD!!, yD!!)
    } catch (e: ParseException) {
        println(e)
        e.printStackTrace()
        Log.d("TAGG", e.toString())
    }

    return Triple(0, 0, 0)
}
@SuppressLint("SimpleDateFormat")
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat(FORMAT_yyyy_MM_dd_HH_mm_ss)
    return formatter.format(Date(millis))
}
@SuppressLint("SimpleDateFormat")
fun currentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(Date())
}

@SuppressLint("SimpleDateFormat")
fun Int.getDayIntervalDateTime(): Pair<String?,String?> {
    val cal = Calendar.getInstance()
    val s = SimpleDateFormat(FORMAT_yyyy_MM_dd, Locale("en"))
    cal.add(Calendar.DAY_OF_YEAR, this)
    val day = s.format(Date(cal.timeInMillis))
    return Pair("$day 00:00:00", "$day 23:59:59")
}
@SuppressLint("SimpleDateFormat")
fun Int.getMonthIntervalDateTime(): Pair<String?,String?> {
    val formatter = SimpleDateFormat(FORMAT_yyyy_MM_dd, Locale("en"))
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, this)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val firstDateOfMonth = calendar.time

    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    val lastDateOfMonth = calendar.time

    val firstDateStr = formatter.format(firstDateOfMonth)
    val lastDateStr = formatter.format(lastDateOfMonth)
    return Pair("$firstDateStr 00:00:00", "$lastDateStr 23:59:59")
}
@SuppressLint("SimpleDateFormat")
fun Int.getYearIntervalDateTime(): Pair<String?,String?> {
    val formatter = SimpleDateFormat(FORMAT_yyyy_MM_dd, Locale("en"))
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR, this)
    calendar.set(Calendar.DAY_OF_YEAR, 1)
    val firstDateOfYear = calendar.time

    calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR))
    val lastDateOfYear = calendar.time

    val firstDateStr = formatter.format(firstDateOfYear)
    val lastDateStr = formatter.format(lastDateOfYear)
    return Pair("$firstDateStr 00:00:00", "$lastDateStr 23:59:59")
}