package com.srabbijan.dashboard.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.dashboard.domain.model.SettingOption
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(Settings.UiState())
    val uiState: StateFlow<Settings.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<Settings.Navigation>()
    val navigation: Flow<Settings.Navigation> = _navigation.receiveAsFlow()

    init {
        val options = listOf(
            SettingOption(
                "Manage categories",
                com.srabbijan.design.R.drawable.category,
                NavigationRoute.Category
            )
        )
        _uiState.update {
            Settings.UiState(settingsOptionList = options)
        }
    }
    fun onEvent(event: Settings.Event) {
        when (event) {
            is Settings.Event.SettingOptionNavigation -> {
                viewModelScope.launch {
                    _navigation.send(Settings.Navigation.GoToManageCategory)
                }
            }
        }
    }

}

object Settings {

    data class UiState(
        val settingsOptionList: List<SettingOption> = emptyList()
    )

    sealed interface Navigation {
        data object GoToManageCategory : Navigation
    }

    sealed interface Event {
        data class SettingOptionNavigation(val route: NavigationRoute) : Event
    }

}
