package com.srabbijan.dashboard.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.dashboard.presentation.home.HomeScreen
import com.srabbijan.dashboard.presentation.home.HomeViewModel
import com.srabbijan.dashboard.presentation.settings.SettingsScreen
import com.srabbijan.dashboard.presentation.settings.SettingsViewModel
import com.srabbijan.design.AppNavigationBar
import com.srabbijan.design.AppNavigationItem
import com.srabbijan.design.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    homeViewModel: HomeViewModel,
    settingViewModel: SettingsViewModel,
    navHostController: NavHostController
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                when (it) {
                    Dashboard.Navigation.GoToAddExpense -> {
                        navHostController.navigate(NavigationRoute.ExpenseAdd)
                    }
                }
            }
    }

    var bottomNavState by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize(),
        containerColor = AppTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(event = Dashboard.Event.AddExpense)
                },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
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
                        homeViewModel
                    )
                }

                1 -> {
                    SettingsScreen(
                        settingViewModel,
                        navHostController
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