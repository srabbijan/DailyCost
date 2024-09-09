package com.srabbijan.expense.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srabbijan.common.utils.Status
import com.srabbijan.common.utils.TransactionType
import com.srabbijan.common.utils.UiText
import com.srabbijan.common.utils.logDebug
import com.srabbijan.expense.domain.model.ExpenseRequest
import com.srabbijan.expense.domain.useCase.ExpenseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val useCase: ExpenseUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseAdd.UiState())
    val uiState: StateFlow<ExpenseAdd.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<ExpenseAdd.Navigation>()
    val navigation: Flow<ExpenseAdd.Navigation> = _navigation.receiveAsFlow()


    fun onEvent(event: ExpenseAdd.Event) {
        when (event) {

            is ExpenseAdd.Event.Insert -> {
                if (validateInputs()) {
                    insert()
                }
            }

            is ExpenseAdd.Event.Update -> {

            }

            is ExpenseAdd.Event.Amount -> {
                _uiState.value = _uiState.value.copy(amount = event.value)
            }

            is ExpenseAdd.Event.GoToBack -> {
                viewModelScope.launch {
                    _navigation.send(ExpenseAdd.Navigation.GoToBack)
                }
            }
            is ExpenseAdd.Event.Type ->{
                _uiState.value = _uiState.value.copy(type = event.value)
            }
        }
    }

    private fun validateInputs(): Boolean {
//        val validationResult = validateInputUseCase.execute(InputType, uiState.value.mobile)
//        _uiState.value = _uiState.value.copy(amountError = validationResult.errorMessage)
//        return validationResult.successful
        val amount = uiState.value.amount.toDoubleOrNull() ?: return false
        return if (amount > 0.0) {
            true
        } else {
            false
        }
    }

    private fun insert() {
        val request = ExpenseRequest(
            type = uiState.value.type,
            amount = uiState.value.amount.toDouble(),
            description = null,
            categoryId = null,
        )
        viewModelScope.launch(Dispatchers.IO) {
            useCase.insert(request).collectLatest { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        val data = response.data as? Boolean ?: return@collectLatest
                        _uiState.update { ExpenseAdd.UiState(isAddCompleted = data) }
                        _navigation.send(ExpenseAdd.Navigation.GoToDashboard)
                    }

                    Status.ERROR -> {
                        _uiState.update { ExpenseAdd.UiState(error = UiText.DynamicString(response.message.toString())) }
                    }

                    Status.LOADING -> {
                        _uiState.update { ExpenseAdd.UiState(isLoading = true) }
                    }
                }
            }
        }
    }

}

object ExpenseAdd {

    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        var isAddCompleted: Boolean = false,

        val amount: String = "",
        val amountError: UiText? = null,
        val type:String = TransactionType.CASH_OUT.value

        )

    sealed interface Navigation {
        data object GoToDashboard : Navigation
        data object GoToBack : Navigation
    }

    sealed interface Event {
        data object GoToBack : Event
        data object Insert : Event
        data object Update : Event
        data class Amount(val value: String) : Event
        data class Type(val value: String):Event
    }

}
