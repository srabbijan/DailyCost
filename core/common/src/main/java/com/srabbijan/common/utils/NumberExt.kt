package com.srabbijan.common.utils

import java.text.DecimalFormat

fun Double?.toCurrencyFormat(countryCode: Country = Country.BD): String {
    val amount = this ?: 0.0
    val dec: DecimalFormat = if (amount.rem(1).equals(0.0)) {
        DecimalFormat("##,##,##,###")
    } else {
        DecimalFormat("##,##,##,###.##")
    }
    when (countryCode) {
        Country.BD -> {
            return " ৳ " + dec.format(amount)
        }
    }

}