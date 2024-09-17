package com.srabbijan.design

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.srabbijan.design.theme.AppTheme

@Composable
fun AppOutLineCard(
    modifier: Modifier = Modifier,
    shape : Shape = AppTheme.shape.container,
    colors : CardColors = CardDefaults.cardColors(
        containerColor = AppTheme.colorScheme.background,
        contentColor = AppTheme.colorScheme.onBackground
    ),
    border: BorderStroke = BorderStroke(1.dp, Color.LightGray),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 12.dp),
        shape = shape,
        colors = colors,
        border = border
    ){
        content()
    }
}