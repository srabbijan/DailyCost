package com.srabbijan.dashboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.srabbijan.common.navigation.FeatureApi
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.dashboard.presentation.DashboardScreen
import com.srabbijan.dashboard.presentation.home.HomeViewModel
import com.srabbijan.dashboard.presentation.report.HtmlToPdfScreen
import com.srabbijan.dashboard.presentation.report.ReportViewModel
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
                val homeViewModel :HomeViewModel = koinViewModel ()
                val settingViewModel : SettingsViewModel = koinViewModel ()
                val reportViewModel : ReportViewModel = koinViewModel ()
                DashboardScreen(
                    reportViewModel = reportViewModel,
                    homeViewModel = homeViewModel,
                    settingViewModel = settingViewModel,
                    navHostController = navHostController
                )
            }
            composable<NavigationRoute.PdfExport> {
                val arg = it.toRoute<NavigationRoute.PdfExport>()
                val data = arg.data
                HtmlToPdfScreen(
                    data,
                    navHostController = navHostController
                )
            }
        }
    }
}