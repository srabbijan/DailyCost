package com.srabbijan.design

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.srabbijan.design.theme.APP_DEFAULT_BUTTON_COLOR
import com.srabbijan.design.theme.ColorDisable
import com.srabbijan.design.theme.ColorError
import com.srabbijan.design.theme.conditional
import com.srabbijan.design.theme.fontRoboto
import com.srabbijan.design.utils.r
import com.srabbijan.design.utils.ssp


@Composable
fun AppActionButton(
    btnName: String,
    modifier: Modifier = Modifier,
    bgColor: Color = APP_DEFAULT_BUTTON_COLOR,
    enable: Boolean = true,
    textColor: Color = Color.Black,
    borderColor: Color? = null,
    isLoading: Boolean = false,
    onActionButtonClick: () -> Unit
) {


    val cornerRadius = 37.r()
    val borderWidth = 1.r()
    Button(
        onClick = {
            if (enable)
                onActionButtonClick()
        },
        enabled = enable,
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            disabledContainerColor = ColorDisable
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.r())
            .wrapContentHeight()
            .clip(RoundedCornerShape(cornerRadius))
            // .background(color = bgColor)
            .conditional(borderColor != null) {
                return@conditional border(
                    borderWidth,
                    APP_DEFAULT_BUTTON_COLOR,
                    shape = RoundedCornerShape(cornerRadius),
                )
            }
    ) {
        if (isLoading){
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = textColor,
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        Text(
            text = btnName,
            style = TextStyle(
                fontFamily = fontRoboto,
                fontSize = 16.ssp(),
                color = textColor,
                fontWeight = FontWeight.W500,
                lineHeight = 28.ssp(),
            ),
            maxLines = 1,
            textAlign = TextAlign.Center,
        )

    }
}

@Composable
fun AppOutlineButton(
    btnName: String,
    modifier: Modifier = Modifier,
    bgColor: Color = Color.White,
    enable: Boolean = true,
    textColor: Color = Color.Black,
    borderColor: Color? =  APP_DEFAULT_BUTTON_COLOR,
    isLoading: Boolean = false,
    onActionButtonClick: () -> Unit
) {

    val cornerRadius = 37.r()
    val borderWidth = 1.5.r()
    Button(
        onClick = {
            if (enable)
                onActionButtonClick()
        },
        enabled = enable,
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            disabledContainerColor = ColorDisable
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.r())
            .wrapContentHeight()
            .clip(RoundedCornerShape(cornerRadius))
            // .background(color = bgColor)
            .conditional(borderColor != null) {
                return@conditional border(
                    borderWidth,
                    borderColor!!,
                    shape = RoundedCornerShape(cornerRadius),
                )
            }
    ) {
        if (isLoading){
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = textColor,
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        Text(
            text = btnName,
            style = TextStyle(
                fontFamily = fontRoboto,
                fontSize = 16.ssp(),
                color = textColor,
                fontWeight = FontWeight.W500,
                lineHeight = 28.ssp(),
            ),
            maxLines = 1,
            textAlign = TextAlign.Center,
        )

    }
}


@Composable
fun AppCancelButton(
    modifier: Modifier = Modifier,
    @StringRes titleStringResId: Int,
    fontSize: TextUnit = 16.ssp(),
    lineHeight: TextUnit = 28.ssp(),
    radius: Dp = 37.dp,
    color: Color = ColorError,
    fontWeight: FontWeight = FontWeight.W700,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = modifier
            .clip(RoundedCornerShape(radius))
            .background(color = Color.White)
            .border(
                1.dp,
                color,
                shape = RoundedCornerShape(radius),
            )
    ) {
        Text(
            text = stringResource(id = titleStringResId),
            style = TextStyle(
                fontFamily = fontRoboto,
                fontSize = fontSize,
                color = color,
                fontWeight = fontWeight,
                lineHeight = lineHeight,
                textAlign = TextAlign.Center
            )
        )
    }
}