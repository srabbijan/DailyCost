package com.srabbijan.expense.domain

import com.srabbijan.database.dto.ExpenseWithCategory
import com.srabbijan.expense.domain.model.ExpenseRequest

interface ExpenseRepository {
    suspend fun insert(request: ExpenseRequest): Result<Boolean>
    suspend fun update(request: ExpenseRequest): Result<Boolean>
    suspend fun fetchById(id: Long): Result<ExpenseWithCategory>
}