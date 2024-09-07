package com.srabbijan.database.dao

import androidx.room.*
import com.srabbijan.database.dto.ExpenseModel
import com.srabbijan.database.entity.CategoryTable
import com.srabbijan.database.entity.ExpenseTable

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<CategoryTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: CategoryTable)
    @Update
    suspend fun update(data: CategoryTable)

    @Transaction
    @Query("DELETE FROM category_table ")
    suspend fun deleteAll()

    @Transaction
    @Query("DELETE FROM category_table  where id =:id")
    suspend fun deleteById( id: Long)

    @Transaction
    @Query(
        "SELECT * from category_table ORDER BY id desc"
    )
    suspend fun fetchAll(): List<CategoryTable>

}
