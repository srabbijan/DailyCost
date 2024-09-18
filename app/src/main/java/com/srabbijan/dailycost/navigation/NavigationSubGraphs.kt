package com.srabbijan.dailycost.navigation

import com.srabbijan.category.navigation.CategoryFeatureApi
import com.srabbijan.dashboard.navigation.DashboardFeatureApi
import com.srabbijan.expense.navigation.ExpenseFeatureApi


data class NavigationSubGraphs(
    val dashboardFeatureApi: DashboardFeatureApi,
    val expenseFeatureApi: ExpenseFeatureApi,
    val categoryFeatureApi: CategoryFeatureApi,
)
