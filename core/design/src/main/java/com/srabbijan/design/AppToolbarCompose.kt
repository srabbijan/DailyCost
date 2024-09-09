package com.srabbijan.design

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.srabbijan.design.theme.AppTheme
import com.srabbijan.design.utils.r

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbarWithBack(
    title: String,
    icon: Painter = painterResource(R.drawable.ic_angle_left),
    onClick: () -> Unit,
) {
    Surface(shadowElevation = 9.r()) {
        TopAppBar(

            title = {
                Text(
                    text = title,
                    style = AppTheme.typography.titleNormal,
                    modifier = Modifier.clickable { onClick() }
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = AppTheme.colorScheme.primary,
            ),
            navigationIcon = {
                IconButton(
                    onClick = { onClick() },
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 10.r())
                            .size(30.r())
                    )
                }
            },
        )
    }
}

