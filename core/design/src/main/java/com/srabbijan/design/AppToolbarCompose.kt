package com.srabbijan.design

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.srabbijan.design.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbarWithBack(
    label: String,
    actions: @Composable (RowScope.() -> Unit)? = null,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppTheme.colorScheme.onPrimary,
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
                IconButton(
                    onClick = { onClick() },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = AppTheme.colorScheme.onPrimary,
                        contentColor = AppTheme.colorScheme.primary,
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                        contentDescription = "Localized description"
                    )
                }
            },
            actions = {
                actions?.invoke(this)
            },
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = AppTheme.colorScheme.onPrimaryContainer.copy(alpha = .2f)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbarHome(
    label: String = "Daily Cost",
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = AppTheme.colorScheme.onPrimary,
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
                navigationIcon?.invoke()
            },
            actions = {
                actions?.invoke(this)
            }
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = AppTheme.colorScheme.onPrimaryContainer.copy(alpha = .2f)
        )
    }

}

