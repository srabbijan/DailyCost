package com.srabbijan.design

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.srabbijan.design.theme.AppTheme


@Composable
fun AppHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colorScheme.separator,
    thickness: Dp = 1.dp
) {
    HorizontalDivider(
        modifier = modifier.padding(vertical = 4.dp),
        thickness = thickness,
        color = color
    )
}