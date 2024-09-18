package com.srabbijan.expense.domain.useCase

data class ExpenseUseCase(
    val insert: InsertEntry,
    val update: UpdateEntry,
    val fetchById: FetchByIdEntry
)