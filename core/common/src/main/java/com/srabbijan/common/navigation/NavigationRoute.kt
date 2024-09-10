package com.srabbijan.common.navigation

import com.srabbijan.common.utils.TransactionType
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
    data object Expense : NavigationRoute()

    @Serializable
    data class ExpenseAdd(
        val type: String = TransactionType.CASH_OUT.value,
    ) : NavigationRoute()

    @Serializable
    data object Category : NavigationRoute()

    @Serializable
    data class CategoryAdd(
        val productId: String? = null
    ) : NavigationRoute()
}

enum class BackToPage(val value: String) {
    DASHBOARD("dashboard"),
    EXPENSE_ADD("expense_add"),
}
