package com.srabbijan.category.domain.useCase

data class CategoryUseCase(
    val insert: InsertEntry,
    val fetchAll: FetchAllEntry
)