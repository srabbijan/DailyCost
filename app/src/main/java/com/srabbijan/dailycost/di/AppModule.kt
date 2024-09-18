package com.srabbijan.dailycost.di

import com.srabbijan.dailycost.navigation.NavigationSubGraphs
import org.koin.dsl.module

val appModule = module {
    single {
        NavigationSubGraphs(get(),get(),get())
    }
}