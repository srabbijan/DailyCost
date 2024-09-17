package com.srabbijan.design

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.srabbijan.design.theme.AppTheme

@Composable
fun AppDateIntervalView(
    showingDate: String,
    offset: Int,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    content: @Composable (RowScope.() -> Unit)? = null
) {
    AppOutLineCard{
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onPrevious()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_angle_left),
                        contentDescription = null,
                    )
                }

                Text(
                    text = showingDate,
                    style = AppTheme.typography.titleNormal
                )

                IconButton(
                    onClick = {
                        if (offset < 0) onNext()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_angle_right),
                        contentDescription = null,
                        tint = if (offset < 0) Color.Black else Color.LightGray
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                content?.let {
                    content()
                }
            }
        }
    }

}