package com.srabbijan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.srabbijan.database.entity.CategoryTable

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
    @Query("SELECT * from category_table ORDER BY name COLLATE NOCASE asc")
    suspend fun fetchAll(): List<CategoryTable>

}
