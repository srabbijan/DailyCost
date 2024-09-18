package com.srabbijan.category.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.srabbijan.category.presentation.add.CategoryAddScreen
import com.srabbijan.category.presentation.add.CategoryAddViewModel
import com.srabbijan.category.presentation.list.CategoryListScreen
import com.srabbijan.category.presentation.list.CategoryListViewModel
import com.srabbijan.common.navigation.FeatureApi
import com.srabbijan.common.navigation.NavigationRoute
import org.koin.androidx.compose.koinViewModel

interface CategoryFeatureApi : FeatureApi
class CategoryFeatureApiImpl : CategoryFeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.apply{
            composable<NavigationRoute.Category> {
                val viewModel: CategoryListViewModel = koinViewModel()
                CategoryListScreen(
                    viewModel = viewModel,
                    navHostController = navHostController
                )
            }
            composable<NavigationRoute.CategoryAdd> {
                val viewModel: CategoryAddViewModel = koinViewModel()
                CategoryAddScreen(
                    viewModel = viewModel,
                    navHostController = navHostController
                )
            }
        }
    }
}