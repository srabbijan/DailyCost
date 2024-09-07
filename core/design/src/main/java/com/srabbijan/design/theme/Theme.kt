package com.srabbijan.design.theme

import android.os.Build
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.srabbijan.design.utils.ssp


private val DarkColorScheme = darkColorScheme(
    primary = ColorPrimaryDark,
    surfaceTint = ColorPrimaryDark,
)

private val LightColorScheme = lightColorScheme(
    primary = ColorPrimaryDark,
    surfaceTint = ColorPrimaryDark
)

@Composable
fun BaseTheme(
    darkTheme: Boolean = false,//isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White)
    systemUiController.setSystemBarsColor(
        color = APP_DEFAULT_COLOR
    )
    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}


@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
    if (isFocused) {
        val imeIsVisible = WindowInsets.isImeVisible
        val focusManager = LocalFocusManager.current
        LaunchedEffect(imeIsVisible) {
            if (imeIsVisible) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}

val darkButtonTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = Color.White,
    fontWeight = FontWeight.Bold,
    lineHeight = 23.ssp(),
    letterSpacing = .5.sp,
    textAlign = TextAlign.Left,
)

val heading1TextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 28.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    textAlign = TextAlign.Center,
    lineHeight = 33.ssp(),
)

//styleName: Body light;
val bodyLightTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W300,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Left
)

//styleName: Body light;
val bodyLMediumTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 18.ssp(),
    fontWeight = FontWeight.W500,
    lineHeight = 21.ssp(),
    textAlign = TextAlign.Left
)

val heading2TextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 32.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 33.ssp(),
)

val preTitleTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 20.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 36.ssp(),
)
val boldBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 28.ssp(),
)


val normalBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.Normal,
    lineHeight = 28.ssp(),
)


val smallBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 14.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.Normal,
    lineHeight = 24.ssp(),
)

//styleName: Body regular;
val bodyRegularTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W300,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Center
)
val bodyRegularSpanStyle = SpanStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W300,
)

val subHeading1TextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 21.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Center
)
val subHeading1SpanStyle = SpanStyle(
    fontFamily = fontRoboto,
    fontSize = 21.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700
)

val bodyXXSRegularTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 12.ssp(),
    color = ColorTextSecondary,
    fontWeight = FontWeight.W300,
    lineHeight = 21.ssp(),
    textAlign = TextAlign.Left
)

val bodyXXSBoldTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 12.ssp(),
    color = Color(0xff3E6FCC),
    fontWeight = FontWeight.W700,
    lineHeight = 21.ssp(),
    textAlign = TextAlign.Left
)

val bodyXSBoldTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 14.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 23.ssp(),
    textAlign = TextAlign.Left
)

val bodyBoldTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Left
)


val bodyXSRegularTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 14.ssp(),
    color = ColorTextSecondary,
    fontWeight = FontWeight.W300,
    lineHeight = 23.ssp(),
    textAlign = TextAlign.Left
)

val bodyMediumTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W500,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Left
)


val subHeadingFormTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W500,
    lineHeight = 19.ssp(),
    textAlign = TextAlign.Left
)


val largeBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 28.ssp(),
)

val smallButtonOrLinkTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W500,
    lineHeight = 28.ssp(),
)

val labelTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W500,
    lineHeight = 28.ssp(),
    letterSpacing = 0.1.sp
)

fun String.parseBold(): AnnotatedString {
    val parts = this.split("<b>", "</b>")
    return buildAnnotatedString {
        var bold = false
        for (part in parts) {
            if (bold) {
                withStyle(
                    style = SpanStyle(
                        fontFamily = fontRoboto,
                        fontWeight = FontWeight.Bold,
                        //fontSize = 14.ssp()
                    )
                ) {
                    append(part)
                }
            } else {
                append(part)
            }
            bold = !bold
        }
    }
}






