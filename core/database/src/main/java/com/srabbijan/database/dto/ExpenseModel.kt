package com.srabbijan.database.dto

data class ExpenseModel(
    val id : Long,
    val type : String,
    val amount: Double,
    val date: String,
    val categoryId: Long? = 0,
    val categoryIcon: Int? = null,
    val categoryName: String? = null,
    val description: String? = null,
    val transactions: Int = 1
)
