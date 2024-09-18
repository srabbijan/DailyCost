package com.srabbijan.expense.data

import com.srabbijan.database.dao.ExpenseDao
import com.srabbijan.database.dto.ExpenseWithCategory
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

    override suspend fun update(request: ExpenseRequest): Result<Boolean> {
        return try {
            expenseDao.update(
                request.toExpenseTable()
            )
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchById(id: Long): Result<ExpenseWithCategory> {
        return try {
            val data = expenseDao.fetchById(id) ?: return Result.failure(Exception("Data not found"))
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}