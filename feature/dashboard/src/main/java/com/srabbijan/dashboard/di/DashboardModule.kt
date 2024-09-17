package com.srabbijan.dashboard.di


import com.srabbijan.dashboard.data.HomeRepositoryImpl
import com.srabbijan.dashboard.domain.HomeRepository
import com.srabbijan.dashboard.domain.useCase.HomeUseCase
import com.srabbijan.dashboard.domain.useCase.FetchAllEntry
import com.srabbijan.dashboard.domain.useCase.FetchSummaryEntry
import com.srabbijan.dashboard.navigation.DashboardFeatureApi
import com.srabbijan.dashboard.navigation.DashboardFeatureApiImpl
import com.srabbijan.dashboard.presentation.DashboardViewModel
import com.srabbijan.dashboard.presentation.home.HomeViewModel
import com.srabbijan.dashboard.presentation.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardNavigationModule = module {
    single<DashboardFeatureApi> { DashboardFeatureApiImpl() }

}
val dashboardRepositoryModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get()) }
}
val dashboardUseCaseModule = module {
    factory { FetchAllEntry(get()) }
    factory { FetchSummaryEntry(get()) }
    factory { HomeUseCase(get(), get()) }
}
val dashboardViewModelModule = module {
    viewModel { DashboardViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { SettingsViewModel() }

}