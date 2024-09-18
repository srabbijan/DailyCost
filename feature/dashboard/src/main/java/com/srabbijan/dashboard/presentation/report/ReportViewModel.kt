package com.srabbijan.dashboard.presentation.report

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srabbijan.common.utils.FORMAT_MMM_yyyy
import com.srabbijan.common.utils.Status
import com.srabbijan.common.utils.UiText
import com.srabbijan.common.utils.getMonthIntervalDateTime
import com.srabbijan.common.utils.getThisMonthFirstDateTime
import com.srabbijan.common.utils.getTodayFirstDateTime
import com.srabbijan.common.utils.getTodayLastDateTime
import com.srabbijan.common.utils.toUiTime
import com.srabbijan.dashboard.domain.useCase.HomeUseCase
import com.srabbijan.database.dto.ExpenseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReportViewModel(
    private val useCase: HomeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(Report.UiState())
    val uiState: StateFlow<Report.UiState> get() = _uiState.asStateFlow()
    val showingDate = mutableStateOf(getTodayFirstDateTime().toUiTime(outFormat = FORMAT_MMM_yyyy))
    val offset = mutableIntStateOf(0)

    fun onEvent(event: Report.Event) {
        when (event) {

            is Report.Event.ThisMonth -> {
                fetchExpenses(
                    startDateTime = getThisMonthFirstDateTime()
                )
            }

            Report.Event.NextInterval -> {
                offset.intValue ++
                generateDate()
            }
            Report.Event.PreInterval -> {
                offset.intValue --
                generateDate()
            }
        }
    }

    private fun fetchExpenses(
        startDateTime: String = getTodayFirstDateTime(),
        endDateTime: String = getTodayLastDateTime()
    ){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.fetchReport(startDateTime, endDateTime).collectLatest { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        val data = response.data as? List<ExpenseModel> ?: return@collectLatest
                        _uiState.update { Report.UiState(reportList = data) }
                    }

                    Status.ERROR -> {
                        _uiState.update { Report.UiState(error = UiText.DynamicString(response.message.toString())) }
                    }

                    Status.LOADING -> {
                        _uiState.update { Report.UiState(isLoading = true) }
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

object Report {

    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val reportList: List<ExpenseModel> = emptyList()
    )

    sealed interface Event {
        data object ThisMonth : Event
        data object NextInterval : Event
        data object PreInterval : Event
    }
}
