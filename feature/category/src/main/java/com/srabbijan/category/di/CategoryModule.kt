package com.srabbijan.category.di



import com.srabbijan.category.domain.CategoryRepository
import com.srabbijan.category.data.CategoryRepositoryImpl
import com.srabbijan.category.domain.useCase.CategoryUseCase
import com.srabbijan.category.domain.useCase.FetchAllEntry
import com.srabbijan.category.domain.useCase.InsertEntry
import com.srabbijan.category.navigation.CategoryFeatureApi
import com.srabbijan.category.navigation.CategoryFeatureApiImpl
import com.srabbijan.category.presentation.add.CategoryAddViewModel
import com.srabbijan.category.presentation.list.CategoryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryNavigationModule = module {
    single<CategoryFeatureApi> { CategoryFeatureApiImpl() }
}
val categoryRepositoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
}

val categoryUseCaseModule = module {
    factory { InsertEntry(get()) }
    factory { FetchAllEntry(get()) }
    factory { CategoryUseCase(get(),get()) }
}
val categoryViewModelModule = module {
    viewModel { CategoryListViewModel(get()) }
    viewModel { CategoryAddViewModel(get()) }
}