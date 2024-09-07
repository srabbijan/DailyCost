package com.srabbijan.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.srabbijan.common.navigation.FeatureApi
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.dashboard.presentation.DashboardScreen
import com.srabbijan.dashboard.presentation.DashboardViewModel
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
                DashboardScreen(
                    viewModel = viewModel,
                    navHostController = navHostController
                )
            }
        }

    }
}