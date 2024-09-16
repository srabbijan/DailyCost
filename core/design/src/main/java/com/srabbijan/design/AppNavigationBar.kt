package com.srabbijan.design

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.srabbijan.common.navigation.NavigationRoute

@Composable
fun AppNavigationBar(
    items: List<AppNavigationItem>,
    onClick: (Int) -> Unit,
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(item.label)
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

data class AppNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: NavigationRoute
)
