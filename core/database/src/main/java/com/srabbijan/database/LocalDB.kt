package com.srabbijan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.srabbijan.database.dao.*
import com.srabbijan.database.entity.*

/**
 * table colum sync info
 * true -> this data is available in server
 * false -> this data is not available in server
 * and this data should send to the server
 * table colum version info
 * 1 -> insert
 * 2 -> update
 * 3 -> delete
 */
@Database(
    entities = [
        ExpenseTable::class,
        CategoryTable::class,
    ],
    version = 100,
    exportSchema = false
)
abstract class LocalDB : RoomDatabase() {
    abstract val expenseDao: ExpenseDao
    abstract val categoryDao: CategoryDao
}
