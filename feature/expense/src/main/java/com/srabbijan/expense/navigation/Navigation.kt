package com.srabbijan.expense.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.srabbijan.common.navigation.FeatureApi
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.expense.presentation.ExpenseAddScreen
import com.srabbijan.expense.presentation.ExpenseViewModel
import org.koin.androidx.compose.koinViewModel

interface ExpenseFeatureApi : FeatureApi
class ExpenseFeatureApiImpl : ExpenseFeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation<NavigationRoute.Expense>(
            startDestination = NavigationRoute.ExpenseAdd
        ) {
            composable<NavigationRoute.ExpenseAdd> {
                val viewModel: ExpenseViewModel = koinViewModel()
                ExpenseAddScreen(
                    viewModel = viewModel,
                    navHostController = navHostController
                )
            }
        }
    }
}