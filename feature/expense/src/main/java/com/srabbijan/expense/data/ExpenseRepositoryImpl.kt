package com.srabbijan.expense.data

import com.srabbijan.database.dao.ExpenseDao
import com.srabbijan.expense.domain.ExpenseRepository
import com.srabbijan.expense.domain.model.ExpenseRequest

class ExpenseRepositoryImpl(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override suspend fun insert(request: ExpenseRequest): Result<Boolean> {
        return try {
             expenseDao.insert(
                 request.toExpenseTable()
             )
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}