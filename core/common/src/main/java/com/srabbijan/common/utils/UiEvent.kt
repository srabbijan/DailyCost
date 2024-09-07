package com.srabbijan.common.utils

sealed class UiEvent {
    data object Success : UiEvent()
    data object NavigateUp : UiEvent()
    data object Loading : UiEvent()
    data class ShowError(val message: UiText) : UiEvent()
}
