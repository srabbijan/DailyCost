package com.srabbijan.design

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.srabbijan.design.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbarWithBack(
    label: String,
    onClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colorScheme.primaryContainer,
            titleContentColor = AppTheme.colorScheme.primary,
        ),
        title = {
            Text(
                label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { onClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {

        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbarHome(
    label: String = "Daily Cost",
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = AppTheme.colorScheme.primaryContainer,
            titleContentColor = AppTheme.colorScheme.primary,
        ),
        title = {
            Text(
                label,
                style = AppTheme.typography.titleNormal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                    contentDescription = "Localized description"
                )
            }
        },
    )
}

