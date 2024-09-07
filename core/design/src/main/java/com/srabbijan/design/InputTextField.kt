package com.srabbijan.design

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.srabbijan.common.utils.UiText
import com.srabbijan.common.utils.isNumber
import com.srabbijan.design.utils.ssp

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    initialValue: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    errorMessage: UiText? = null,
    isError: Boolean = false,
    isVisible: Boolean = false,
    isEnable: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLine: Int = 1,
    onValueChange: (String) -> Unit = {},
) {
    val isKeyboardTypeNumber =
        keyboardType == KeyboardType.Phone || keyboardType == KeyboardType.Number
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        value = if (isKeyboardTypeNumber) {
            if (isNumber(initialValue)) initialValue else ""
        } else initialValue,
        onValueChange = {
            if (isKeyboardTypeNumber) {
                if (isNumber(it)) onValueChange(it)
            } else onValueChange(it)
        },
        enabled = isEnable,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        modifier = modifier,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = errorMessage!!.asString(context),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = modifier
                )
            }
        },
        label = { Text(placeholder, fontSize = 20.ssp()) },
        textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface),
        maxLines = maxLine,
        singleLine = singleLine,
        interactionSource = interactionSource,
        visualTransformation = if (keyboardType == KeyboardType.Password) {
            if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
    )


}
