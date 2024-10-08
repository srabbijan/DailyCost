package com.srabbijan.dashboard.data

import com.srabbijan.common.utils.TransactionType
import com.srabbijan.dashboard.domain.HomeRepository
import com.srabbijan.dashboard.domain.model.SummaryModel
import com.srabbijan.database.dao.ExpenseDao
import com.srabbijan.database.dto.ExpenseModel

class HomeRepositoryImpl(
    private val expenseDao: ExpenseDao
) : HomeRepository {
    override suspend fun fetchAll(startDate: String, endDate: String): Result<List<ExpenseModel>> {
        return try {
            val response = expenseDao.fetchBetweenDate(startDate, endDate)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchSummary(startDate: String, endDate: String): Result<SummaryModel> {
        return try {
            val cashIn = expenseDao.sumAmountBetweenDate(startDate, endDate)?:0.0
            val cashOut = expenseDao.sumAmountBetweenDate(startDate, endDate, type = TransactionType.CASH_OUT.value)?:0.0
            Result.success(
                SummaryModel(
                    balance = cashIn-cashOut,
                    expense = cashOut,
                    income = cashIn
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun fetchReport(startDate: String, endDate: String): Result<List<ExpenseModel>> {
        return try {
            val response = expenseDao.fetchReportBetweenDate(startDate, endDate)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}