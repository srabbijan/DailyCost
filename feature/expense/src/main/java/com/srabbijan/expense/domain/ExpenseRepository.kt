package com.srabbijan.expense.domain

import com.srabbijan.expense.domain.model.ExpenseRequest

interface ExpenseRepository {
    suspend fun insert(request: ExpenseRequest): Result<Boolean>
}