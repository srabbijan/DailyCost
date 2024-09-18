package com.srabbijan.category.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srabbijan.category.domain.useCase.CategoryUseCase
import com.srabbijan.common.utils.Status
import com.srabbijan.common.utils.UiText
import com.srabbijan.database.entity.CategoryTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryListViewModel(
    private val useCase: CategoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryList.UiState())
    val uiState: StateFlow<CategoryList.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<CategoryList.Navigation>()
    val navigation: Flow<CategoryList.Navigation> = _navigation.receiveAsFlow()

    fun onEvent(event: CategoryList.Event) {
        when (event) {

            is CategoryList.Event.InitialDataFetch -> {
                findInitialData()
            }

            is CategoryList.Event.GoToBack -> {
                viewModelScope.launch {
                    _navigation.send(CategoryList.Navigation.GoToBack)
                }
            }

            CategoryList.Event.GoToCategoryAdd -> {
                viewModelScope.launch {
                    _navigation.send(CategoryList.Navigation.GoToCategoryAdd)
                }
            }
        }
    }
    private fun findInitialData(){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.fetchAll().collectLatest { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        val data = response.data as? List<CategoryTable> ?: return@collectLatest
                        _uiState.update { CategoryList.UiState(categoryList = data) }
//                        _uiState.value = _uiState.value.copy(categoryList = data)
                    }

                    Status.ERROR -> {
                        _uiState.update { CategoryList.UiState(error = UiText.DynamicString(response.message.toString())) }
                    }

                    Status.LOADING -> {
                        _uiState.update { CategoryList.UiState(isLoading = true) }
                    }
                }
            }
        }
    }
}

object CategoryList {

    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val categoryList: List<CategoryTable> = emptyList(),
        )

    sealed interface Navigation {
        data object GoToCategoryAdd : Navigation
        data object GoToBack : Navigation
    }

    sealed interface Event {
        data object GoToBack : Event
        data object InitialDataFetch : Event
        data object GoToCategoryAdd : Event
    }
}
