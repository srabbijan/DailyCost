package com.srabbijan.dailycost.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.srabbijan.common.navigation.NavigationRoute

@Composable
fun AppNavigation(
    navigationSubGraphs: NavigationSubGraphs
) {
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = NavigationRoute.Dashboard
    ) {
        navigationSubGraphs.dashboardFeatureApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.expenseFeatureApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.categoryFeatureApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
    }
}