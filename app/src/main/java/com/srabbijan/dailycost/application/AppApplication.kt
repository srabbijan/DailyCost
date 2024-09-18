package com.srabbijan.dailycost.application

import android.app.Application
import com.srabbijan.category.di.categoryNavigationModule
import com.srabbijan.category.di.categoryRepositoryModule
import com.srabbijan.category.di.categoryUseCaseModule
import com.srabbijan.category.di.categoryViewModelModule
import com.srabbijan.dailycost.di.appModule
import com.srabbijan.dashboard.di.*
import com.srabbijan.database.di.databaseModule
import com.srabbijan.expense.di.expenseNavigationModule
import com.srabbijan.expense.di.expenseRepositoryModule
import com.srabbijan.expense.di.expenseUseCaseModule
import com.srabbijan.expense.di.expenseViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AppApplication : Application() {
    companion object {
        val allAppModules = listOf(
            dashboardNavigationModule,
            dashboardRepositoryModule,
            dashboardUseCaseModule,
            dashboardViewModelModule,

            expenseNavigationModule,
            expenseRepositoryModule,
            expenseUseCaseModule,
            expenseViewModelModule,

            categoryNavigationModule,
            categoryRepositoryModule,
            categoryUseCaseModule,
            categoryViewModelModule,

            appModule,
            databaseModule
        )
    }

    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@AppApplication)
            modules(allAppModules)
        }
    }
}