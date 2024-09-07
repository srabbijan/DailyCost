package com.srabbijan.design

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.srabbijan.design.theme.APP_DEFAULT_COLOR_LIGHT
import com.srabbijan.design.theme.ColorDisable
import com.srabbijan.design.utils.r
import com.srabbijan.design.utils.ssp

@Composable
fun AppDateIntervalView(
    showingDate: String,
    offset: Int,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    content: @Composable (RowScope.() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 8.dp, bottomStart = 8.dp))
            .background(APP_DEFAULT_COLOR_LIGHT)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                modifier = Modifier.size(25.r()),
                onClick = {
                    onPrevious()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_angle_left),
                    contentDescription = null,
                )
            }

            Text(text = showingDate, color = Color.Black, fontSize = 18.ssp())

            IconButton(
                modifier = Modifier.size(25.r()),
                onClick = {
                    if (offset < 0) onNext()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_angle_right),
                    contentDescription = null,
                    tint = if (offset < 0) Color.Black else ColorDisable
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            content?.let {
                content()
            }
        }
    }
}