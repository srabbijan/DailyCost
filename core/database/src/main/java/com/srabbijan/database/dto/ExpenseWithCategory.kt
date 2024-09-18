package com.srabbijan.database.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.srabbijan.database.entity.CategoryTable
import com.srabbijan.database.entity.ExpenseTable

data class ExpenseWithCategory(
    @Embedded val expense: ExpenseTable?,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: CategoryTable?
)
