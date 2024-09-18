package com.srabbijan.category.domain

import com.srabbijan.category.domain.model.CategoryRequest
import com.srabbijan.database.entity.CategoryTable

interface CategoryRepository {
    suspend fun insert(request: CategoryRequest): Result<Boolean>
    suspend fun fetchAll(): Result<List<CategoryTable>>
}