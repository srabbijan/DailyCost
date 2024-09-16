package com.srabbijan.dashboard.presentation.home

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

class HomeViewModel(
    private val dashboardUseCases: DashboardUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(Home.UiState())
    val uiState: StateFlow<Home.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<Home.Navigation>()
    val navigation: Flow<Home.Navigation> = _navigation.receiveAsFlow()

    val showingDate = mutableStateOf(getTodayFirstDateTime().toUiTime(outFormat = FORMAT_MMM_yyyy))
    val offset = mutableIntStateOf(0)

    fun onEvent(event: Home.Event) {
        when (event) {

            is Home.Event.ThisMonth -> {
                fetchSummary(
                    startDateTime = getThisMonthFirstDateTime()
                )
            }

            Home.Event.NextInterval -> {
                offset.intValue ++
                generateDate()
            }
            Home.Event.PreInterval -> {
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
//                        _uiState.update { Home.UiState(summaryData = data) }
                        _uiState.value = _uiState.value.copy(summaryData = data)
                        fetchExpenses(startDateTime, endDateTime)
                    }

                    Status.ERROR -> {
//                        _uiState.update { Home.UiState(error = UiText.DynamicString(response.message.toString())) }
                    }

                    Status.LOADING -> {
//                        _uiState.update { Home.UiState(isLoading = true) }
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
//                        _uiState.update { Home.UiState(expenseList = data) }
                    }

                    Status.ERROR -> {
//                        _uiState.update { Home.UiState(error = UiText.DynamicString(response.message.toString())) }
                    }

                    Status.LOADING -> {
//                        _uiState.update { Home.UiState(isLoading = true) }
                    }
                }
            }
        }
    }
    private fun generateDate(){
        val date = offset.intValue.getMonthIntervalDateTime()
        showingDate.value = "${date.first?.toUiTime(outFormat = FORMAT_MMM_yyyy)}"
        fetchSummary(date.first!!, date.second!!)
    }
}

object Home {

    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val summaryData: SummaryModel? = null,
        val expenseList: List<ExpenseModel> = emptyList()
    )


    sealed interface Navigation {

    }

    sealed interface Event {

        data object ThisMonth : Event
        data object NextInterval : Event
        data object PreInterval : Event
    }

}
