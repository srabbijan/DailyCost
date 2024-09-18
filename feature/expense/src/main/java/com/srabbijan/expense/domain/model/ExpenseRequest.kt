package com.srabbijan.expense.domain.model

import com.srabbijan.common.utils.TransactionType
import com.srabbijan.common.utils.getCurrentDateTime

data class ExpenseRequest(
    val id: Long? = null,
    val type: String = TransactionType.CASH_OUT.value,
    val amount: Double,
    val description: String?,
    val categoryId: Long?,
    val date: String = getCurrentDateTime()
)
