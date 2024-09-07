package com.srabbijan.expense.data

import com.srabbijan.database.entity.ExpenseTable
import com.srabbijan.expense.domain.model.ExpenseRequest

fun ExpenseRequest.toExpenseTable(): ExpenseTable {
    return ExpenseTable(
        id = 0,
        type = type,
        amount = amount,
        categoryId = categoryId,
        description = description,
    )
}