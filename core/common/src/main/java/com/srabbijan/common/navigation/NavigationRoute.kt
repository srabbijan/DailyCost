package com.srabbijan.common.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute {
    @Serializable
    data object Dashboard : NavigationRoute()

    @Serializable
    data object Home : NavigationRoute()

    @Serializable
    data object Report : NavigationRoute()

    @Serializable
    data object Settings : NavigationRoute()

    @Serializable
    data object Expense : NavigationRoute()

    @Serializable
    data class ExpenseAdd(
        val expenseId: Long? = null
    ) : NavigationRoute()

    @Serializable
    data object Category : NavigationRoute()

    @Serializable
    data object CategoryAdd: NavigationRoute()
}
