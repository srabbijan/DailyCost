package com.srabbijan.dashboard.domain

import com.srabbijan.dashboard.domain.model.SummaryModel
import com.srabbijan.database.dto.ExpenseModel

interface HomeRepository {
    suspend fun fetchAll(startDate:String,endDate:String): Result<List<ExpenseModel>>
    suspend fun fetchReport(startDate:String,endDate:String): Result<List<ExpenseModel>>
    suspend fun fetchSummary(startDate:String,endDate:String): Result<SummaryModel>
}