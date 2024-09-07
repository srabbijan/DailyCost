package com.srabbijan.dashboard.presentation

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srabbijan.common.utils.*
import com.srabbijan.dashboard.domain.model.SummaryModel
import com.srabbijan.dashboard.domain.useCase.DashboardUseCase
import com.srabbijan.database.dto.ExpenseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val dashboardUseCases: DashboardUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(Dashboard.UiState())
    val uiState: StateFlow<Dashboard.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<Dashboard.Navigation>()
    val navigation: Flow<Dashboard.Navigation> = _navigation.receiveAsFlow()

    val showingDate = mutableStateOf(getTodayFirstDateTime().toUiTime(outFormat = FORMAT_MMM_yyyy))
    val offset = mutableIntStateOf(0)

    fun onEvent(event: Dashboard.Event) {
        when (event) {

            is Dashboard.Event.AddExpense -> {
                viewModelScope.launch {
                    _navigation.send(Dashboard.Navigation.GoToAddExpense)
                }
            }

            is Dashboard.Event.AddIncome -> {
                viewModelScope.launch {
                    _navigation.send(Dashboard.Navigation.GoToAddIncome)
                }
            }

            is Dashboard.Event.ThisMonth -> {
                fetchSummary(
                    startDateTime = getThisMonthFirstDateTime()
                )
            }

            Dashboard.Event.NextInterval -> {
                offset.intValue ++
                generateDate()
            }
            Dashboard.Event.PreInterval -> {
                offset.intValue --
                generateDate()
            }
        }
    }

    private fun fetchSummary(
        startDateTime: String = getTodayFirstDateTime(),
        endDateTime: String = getTodayLastDateTime()
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            dashboardUseCases.fetchSummary(startDateTime, endDateTime).collectLatest { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        val data = response.data as? SummaryModel ?: return@collectLatest
//                        _uiState.update { Dashboard.UiState(summaryData = data) }
                        _uiState.value = _uiState.value.copy(summaryData = data)
                        fetchExpenses()
                    }

                    Status.ERROR -> {
//                        _uiState.update { Dashboard.UiState(error = UiText.DynamicString(response.message.toString())) }
                    }

                    Status.LOADING -> {
//                        _uiState.update { Dashboard.UiState(isLoading = true) }
                    }
                }
            }
        }
    }
    private fun fetchExpenses(
        startDateTime: String = getTodayFirstDateTime(),
        endDateTime: String = getTodayLastDateTime()
    ){
        viewModelScope.launch(Dispatchers.IO) {
            dashboardUseCases.fetchAll(startDateTime, endDateTime).collectLatest { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        val data = response.data as? List<ExpenseModel> ?: return@collectLatest
                        _uiState.value = _uiState.value.copy(expenseList = data)
//                        _uiState.update { Dashboard.UiState(expenseList = data) }
                    }

                    Status.ERROR -> {
//                        _uiState.update { Dashboard.UiState(error = UiText.DynamicString(response.message.toString())) }
                    }

                    Status.LOADING -> {
//                        _uiState.update { Dashboard.UiState(isLoading = true) }
                    }
                }
            }
        }
    }
    private fun generateDate(){
        val date = offset.intValue.getMonthIntervalDateTime()
        showingDate.value = "${date.first?.toUiTime(outFormat = FORMAT_MMM_yyyy)}"
        fetchExpenses(date.first!!, date.second!!)
    }
}

object Dashboard {

    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val summaryData: SummaryModel? = null,
        val expenseList: List<ExpenseModel> = emptyList()
    )


    sealed interface Navigation {
        data object GoToAddIncome : Navigation
        data object GoToAddExpense : Navigation
    }

    sealed interface Event {
        data object AddIncome : Event
        data object AddExpense : Event
        data object ThisMonth : Event
        data object NextInterval : Event
        data object PreInterval : Event
    }

}
