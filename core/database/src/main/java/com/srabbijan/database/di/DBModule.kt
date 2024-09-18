package com.srabbijan.database.di

import androidx.room.Room
import com.srabbijan.database.LocalDB
import org.koin.dsl.module

val databaseModule = module {
    // Define a singleton for a Repository
    single {
        Room.databaseBuilder(get(), LocalDB::class.java, "srabbijan_daily_cost_db")
            .fallbackToDestructiveMigration() // This will delete data on version upgrade
            .build()
    }

    // Provide the DAO instance
    single { get<LocalDB>().expenseDao }
    single { get<LocalDB>().categoryDao }
}
