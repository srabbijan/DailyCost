package com.srabbijan.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.srabbijan.common.navigation.FeatureApi
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.dashboard.presentation.DashboardScreen
import com.srabbijan.dashboard.presentation.DashboardViewModel
import com.srabbijan.dashboard.presentation.home.HomeViewModel
import com.srabbijan.dashboard.presentation.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

interface DashboardFeatureApi : FeatureApi
class DashboardFeatureApiImpl : DashboardFeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.apply {
            composable<NavigationRoute.Dashboard> {
                val viewModel: DashboardViewModel = koinViewModel()
                val homeViewModel :HomeViewModel = koinViewModel ()
                val settingViewModel : SettingsViewModel = koinViewModel ()
                DashboardScreen(
                    viewModel = viewModel,
                    homeViewModel = homeViewModel,
                    settingViewModel = settingViewModel,
                    navHostController = navHostController
                )
            }
        }
    }
}