package com.srabbijan.dashboard.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.dashboard.presentation.home.HomeScreen
import com.srabbijan.dashboard.presentation.home.HomeViewModel
import com.srabbijan.dashboard.presentation.report.ReportScreen
import com.srabbijan.dashboard.presentation.report.ReportViewModel
import com.srabbijan.dashboard.presentation.settings.SettingsScreen
import com.srabbijan.dashboard.presentation.settings.SettingsViewModel
import com.srabbijan.design.AppNavigationBar
import com.srabbijan.design.AppNavigationItem
import com.srabbijan.design.theme.AppTheme

@Composable
fun DashboardScreen(
    reportViewModel: ReportViewModel,
    homeViewModel: HomeViewModel,
    settingViewModel: SettingsViewModel,
    navHostController: NavHostController
) {

    var bottomNavState by rememberSaveable {
        mutableIntStateOf(0)
    }
    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize(),
        containerColor = AppTheme.colorScheme.primaryContainer,
        bottomBar = {
            AppNavigationBar(
                listOf(
                    AppNavigationItem(
                        label = "Home",
                        icon = Icons.Outlined.Home,
                        route = NavigationRoute.Home
                    ),
                    AppNavigationItem(
                        label = "Report",
                        icon = Icons.Outlined.Receipt,
                        route = NavigationRoute.Report
                    ),

                    AppNavigationItem(
                        label = "Settings",
                        icon = Icons.Outlined.Settings,
                        route = NavigationRoute.Settings
                    )
                )
            ) {
                bottomNavState = it
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(
                PaddingValues(
                    0.dp,
                    0.dp,
                    0.dp,
                    innerPadding.calculateBottomPadding()
                )
            )
        ) {
            when (bottomNavState) {
                0 -> {
                    HomeScreen(
                        homeViewModel,
                        navHostController = navHostController
                    )
                }

                1 -> {
                    ReportScreen(
                        reportViewModel
                    )
                }

                2 -> {
                    SettingsScreen(
                        settingViewModel,
                        navHostController
                    )
                }
            }
        }
    }
}