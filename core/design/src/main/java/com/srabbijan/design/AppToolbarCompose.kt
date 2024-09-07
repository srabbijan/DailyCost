package com.srabbijan.design

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.srabbijan.design.theme.APP_DEFAULT_COLOR
import com.srabbijan.design.theme.boldBodyTextStyle
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
                    style = boldBodyTextStyle.copy(color = Color.Black, fontSize = 16.sp),
                    modifier = Modifier.clickable { onClick() }
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = APP_DEFAULT_COLOR
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

