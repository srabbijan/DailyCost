package com.srabbijan.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _navigation = Channel<Dashboard.Navigation>()
    val navigation: Flow<Dashboard.Navigation> = _navigation.receiveAsFlow()

    fun onEvent(event: Dashboard.Event) {
        when (event) {
            is Dashboard.Event.AddExpense -> {
                viewModelScope.launch {
                    _navigation.send(Dashboard.Navigation.GoToAddExpense)
                }
            }
        }
    }
}

object Dashboard {

    sealed interface Navigation {
        data object GoToAddExpense : Navigation
    }

    sealed interface Event {
        data object AddExpense : Event
    }

}
