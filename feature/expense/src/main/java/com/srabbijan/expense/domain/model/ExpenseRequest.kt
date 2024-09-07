package com.srabbijan.expense.domain.model

import com.srabbijan.common.utils.TransactionType

data class ExpenseRequest(
    val type: String = TransactionType.CASH_OUT.value,
    val amount: Double,
    val description: String?,
    val categoryId: Long?
)
