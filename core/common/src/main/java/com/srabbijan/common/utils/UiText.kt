package com.srabbijan.common.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {

    data class DynamicString(val value: String) : UiText()

    class StringResource(
        @StringRes val res: Int,
        val args:Array<Any> = arrayOf()
    ) : UiText()

    data object Idle : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> {
                value
            }

            is StringResource -> {
                context.getString(res, *args)
            }

            Idle -> ""
        }
    }


    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> {
                value
            }

            is StringResource -> {
                stringResource(res, *args)
            }

            Idle -> ""
        }
    }


}