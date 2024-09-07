package com.srabbijan.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.srabbijan.common.utils.getCurrentDateTime

@Entity(tableName = "expense_table")
data class ExpenseTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "amount") val amount: Double = 0.0,
    @ColumnInfo(name = "date") val date: String = getCurrentDateTime(),
    @ColumnInfo(name = "category_id") val categoryId: Long? = 0,
    @ColumnInfo(name = "description") val description: String? = "",
)