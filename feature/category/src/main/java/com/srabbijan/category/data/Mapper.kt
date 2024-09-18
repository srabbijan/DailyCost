package com.srabbijan.category.data

import com.srabbijan.category.domain.model.CategoryRequest
import com.srabbijan.database.entity.CategoryTable

fun CategoryRequest.toCategoryTable(): CategoryTable {
    return CategoryTable(
        id = 0,
        name = name,
        icon = icon,
        color = color,
    )
}