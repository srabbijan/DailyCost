package com.srabbijan.design.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val designScreenHeight = 930.0
const val designScreenWidth = 430.0

var deviceHeight: Int = 0
var deviceWidth: Int = 0

@Composable
fun Int.w(): Dp {
    val configuration = LocalConfiguration.current
    deviceWidth = configuration.screenWidthDp
    return ((deviceWidth / designScreenWidth) * this).dp
}


@Composable
fun Int.r(): Dp {
    val configuration = LocalConfiguration.current
    deviceHeight = configuration.screenHeightDp
    deviceWidth = configuration.screenWidthDp
    val scaleWidth = deviceWidth / designScreenWidth
    val scaleHeight = deviceHeight / designScreenHeight
    return (this * scaleWidth.coerceAtMost(scaleHeight)).dp
}

@Composable
fun Float.r(): Dp {
    val configuration = LocalConfiguration.current
    deviceHeight = configuration.screenHeightDp
    deviceWidth = configuration.screenWidthDp
    val scaleWidth = deviceWidth / designScreenWidth
    val scaleHeight = deviceHeight / designScreenHeight
    return (this * scaleWidth.coerceAtMost(scaleHeight)).dp
}

@Composable
fun Double.r(): Dp {
    val configuration = LocalConfiguration.current
    deviceHeight = configuration.screenHeightDp
    deviceWidth = configuration.screenWidthDp
    val scaleWidth = deviceWidth / designScreenWidth
    val scaleHeight = deviceHeight / designScreenHeight
    return (this * scaleWidth.coerceAtMost(scaleHeight)).dp
}


@Composable
fun Int.h(): Dp {
    val configuration = LocalConfiguration.current
    deviceHeight = configuration.screenHeightDp
    return ((deviceHeight / designScreenHeight) * this).dp
}
@Composable
fun Double.h(): Dp {
    val configuration = LocalConfiguration.current
    deviceHeight = configuration.screenHeightDp
    return ((deviceHeight / designScreenHeight) * this).dp
}
fun Int.ssp(): TextUnit {
    if (deviceHeight == 0 || deviceWidth == 0)
        return this.sp
    val scaleWidth = deviceWidth / designScreenWidth
    val scaleHeight = deviceHeight / designScreenHeight
    return (this * scaleWidth.coerceAtMost(scaleHeight)).sp
}
