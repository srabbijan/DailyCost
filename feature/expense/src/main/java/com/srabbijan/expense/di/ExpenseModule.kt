package com.srabbijan.expense.di



import com.srabbijan.expense.data.ExpenseRepositoryImpl
import com.srabbijan.expense.domain.ExpenseRepository
import com.srabbijan.expense.domain.useCase.ExpenseUseCase
import com.srabbijan.expense.domain.useCase.InsertEntry
import com.srabbijan.expense.navigation.ExpenseFeatureApi
import com.srabbijan.expense.navigation.ExpenseFeatureApiImpl
import com.srabbijan.expense.presentation.ExpenseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val expenseNavigationModule = module {
    single<ExpenseFeatureApi> { ExpenseFeatureApiImpl() }
}
val expenseRepositoryModule = module {
    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }
}

val expenseUseCaseModule = module {
    factory { InsertEntry(get()) }
    factory { ExpenseUseCase(get()) }
}
val expenseViewModelModule = module {
    viewModel { ExpenseViewModel(get()) }
}