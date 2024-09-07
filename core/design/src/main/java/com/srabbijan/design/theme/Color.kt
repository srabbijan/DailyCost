package com.srabbijan.design.theme

import androidx.compose.material3.CardColors
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val ColorPrimaryDark = Color(0xFF005DFF)

val ColorError = Color(0xFFFF0000)
val ColorSuccess = Color(0xFF1C8D5D)

val ColorTextPrimary = Color(0xFF021561)
val ColorTextSecondary = Color(0xFF606888)

val ColorBorder = Color(0xFFDEDEDE)
val ColorTextFieldPlaceholder = Color(0xFFA7AEC1)

val ColorDisable = Color(0xFFCECECE)
val ColorCardBg = Color(0xFFF5F5F5)

val APP_DEFAULT_COLOR = Color(0xFFFFCB37)
val APP_DEFAULT_COLOR_LIGHT = Color(0xFFFFEEB3)

val APP_DEFAULT_BUTTON_COLOR = Color(0xFFEED450)

val AppCardColor = CardColors(
    containerColor = ColorCardBg,
    contentColor = Color.Black,
    disabledContainerColor = ColorDisable,
    disabledContentColor = Color.Black
)
val AppBrushReversed = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFFF0F0F0),
        Color(0xFFF3F6FF),
        Color(0xFFDBE3FF),
    ),
)
val MaliciousBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xffFFD4C6),
        Color(0xffFFFFFF),
        Color(0xffFFFFFF),
        Color(0xffFFFFFF),

        ),
)
val SuspiciousBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xffFFEBC0),
        Color(0xffFFFFFF),
        Color(0xffFFFFFF),
        Color(0xffFFFFFF),
    ),
)
val HighGreenBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFF87CE6D),
        Color(0xFF82DC61),
    ),
)

val MediumOrangeBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFA3D00),
        Color(0xFFFFAC00),
        Color(0xFFFAAC03),
        Color(0xFFDBE9FF),
        Color(0xFFDCE9FF),
    ),
)

val LowRedBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFA3D00),
        Color(0xFFDBE9FF),
        Color(0xFFDCE9FF),
        Color(0xFFDBE9FF),
        Color(0xFFDCE9FF),
    ),
)
