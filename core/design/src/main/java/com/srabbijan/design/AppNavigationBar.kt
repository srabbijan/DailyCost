package com.srabbijan.design

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.design.theme.AppTheme

@Composable
fun AppNavigationBar(
    items: List<AppNavigationItem>,
    onClick: (Int) -> Unit,
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(
            thickness = 1.dp,
            color = AppTheme.colorScheme.onPrimaryContainer.copy(alpha = .2f)
        )
        NavigationBar(
            containerColor = AppTheme.colorScheme.primaryContainer,
            contentColor = AppTheme.colorScheme.onPrimaryContainer
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors().copy(
                        selectedIconColor = AppTheme.colorScheme.onPrimaryContainer,
                        selectedTextColor = AppTheme.colorScheme.onPrimaryContainer,
                        selectedIndicatorColor = AppTheme.colorScheme.primary.copy(alpha = 0.2f),
                    ),
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )
                    },
                    label = {
                        Text(item.label, style = AppTheme.typography.labelNormal)
                    },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        onClick(index)
                    }
                )
            }
        }
    }
}

data class AppNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: NavigationRoute
)
