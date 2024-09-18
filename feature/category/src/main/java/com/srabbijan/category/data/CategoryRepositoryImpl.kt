package com.srabbijan.category.data

import com.srabbijan.design.R
import com.srabbijan.category.domain.CategoryRepository
import com.srabbijan.category.domain.model.CategoryRequest
import com.srabbijan.database.dao.CategoryDao
import com.srabbijan.database.entity.CategoryTable

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override suspend fun insert(request: CategoryRequest): Result<Boolean> {
        return try {
            categoryDao.insert(
                 request.toCategoryTable()
             )
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchAll(): Result<List<CategoryTable>> {
        return try {
            val data = categoryDao.fetchAll()
            if (data.isEmpty()){
                val defaultCategory = listOf(
                    CategoryTable(id = 1, name = "Cafe", icon = R.drawable.cafe, color = null),
                    CategoryTable(id = 2, name = "Others", icon = R.drawable.category, color = null),
                    CategoryTable(id = 3, name = "Donate", icon = R.drawable.donate, color = null),
                    CategoryTable(id = 4, name = "Education", icon = R.drawable.education, color = null),
                    CategoryTable(id = 5, name = "Electronics", icon = R.drawable.electronics, color = null),
                    CategoryTable(id = 6, name = "Fuel", icon = R.drawable.fuel, color = null),
                    CategoryTable(id = 7, name = "Gifts", icon = R.drawable.gifts, color = null),
                    CategoryTable(id = 8, name = "Groceries", icon = R.drawable.groceries, color = null),
                    CategoryTable(id = 9, name = "Health", icon = R.drawable.health, color = null),
                    CategoryTable(id = 10, name = "Institute", icon = R.drawable.institute, color = null),
                    CategoryTable(id = 11, name = "Laundry", icon = R.drawable.laundry, color = null),
                    CategoryTable(id = 12, name = "Liquor", icon = R.drawable.liquor, color = null),
                    CategoryTable(id = 13, name = "Maintenance", icon = R.drawable.maintenance, color = null),
                    CategoryTable(id = 14, name = "Money", icon = R.drawable.money, color = null),
                    CategoryTable(id = 15, name = "Party", icon = R.drawable.party, color = null),
                    CategoryTable(id = 16, name = "Restaurant", icon = R.drawable.restaurant, color = null),
                    CategoryTable(id = 17, name = "Savings", icon = R.drawable.savings, color = null),
                    CategoryTable(id = 18, name = "Self Development", icon = R.drawable.self_development, color = null),
                    CategoryTable(id = 19, name = "Sport", icon = R.drawable.sport, color = null),
                    CategoryTable(id = 20, name = "Transport", icon = R.drawable.transportation, color = null),
                )
                categoryDao.insertAll(defaultCategory)
                return Result.success(defaultCategory)
            }
            else
            {
                Result.success(data)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}