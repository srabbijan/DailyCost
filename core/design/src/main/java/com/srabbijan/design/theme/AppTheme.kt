package com.srabbijan.design.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val darkColorScheme = AppColorScheme(
    background = onBackground,
    onBackground = background,
    primary = onPrimary,
    onPrimary = primary,
    primaryContainer = onPrimaryContainer,
    onPrimaryContainer = primaryContainer,
    secondary = onSecondary,
    onSecondary = secondary,
    separator = outline,
    error = onError,
    onError = error
)

private val lightColorScheme = AppColorScheme(
    background = background,
    onBackground = onBackground,
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    secondary = secondary,
    onSecondary = onSecondary,
    separator = outline,
    error = error,
    onError = onError
)

private val typography = AppTypography(
    titleLarge = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleNormal = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    paragraph = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    labelNormal = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = roboto,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
)

private val shape = AppShape(
    container = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(50),
//    circular = ImperfectCircleShape()
)

private val size = AppSize(
    large = 24.dp,
    medium = 16.dp,
    normal = 12.dp,
    small = 8.dp
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme

    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shape,
        LocalAppSize provides size,
        LocalIndication provides ripple(),
        content = content
    )
}

object AppTheme {

    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current

    val size: AppSize
        @Composable get() = LocalAppSize.current
}