package com.srabbijan.category.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srabbijan.category.domain.model.CategoryRequest
import com.srabbijan.category.domain.useCase.CategoryUseCase
import com.srabbijan.common.utils.Status
import com.srabbijan.common.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import com.srabbijan.design.R
import kotlinx.coroutines.launch

class CategoryAddViewModel(
    private val useCase: CategoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryAdd.UiState())
    val uiState: StateFlow<CategoryAdd.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<CategoryAdd.Navigation>()
    val navigation: Flow<CategoryAdd.Navigation> = _navigation.receiveAsFlow()

    init {
        val defaultCategoryIcon = listOf(
            R.drawable.cafe,
            R.drawable.category,
            R.drawable.donate,
            R.drawable.education,
            R.drawable.electronics,
            R.drawable.fuel,
            R.drawable.gifts,
            R.drawable.groceries,
            R.drawable.health,
            R.drawable.institute,
            R.drawable.laundry,
            R.drawable.liquor,
            R.drawable.maintenance,
            R.drawable.money,
            R.drawable.party,
            R.drawable.restaurant,
            R.drawable.savings,
            R.drawable.self_development,
            R.drawable.sport,
            R.drawable.transportation,
        )
        _uiState.value = _uiState.value.copy(defaultCategoryIcon = defaultCategoryIcon)
    }

    fun onEvent(event: CategoryAdd.Event) {
        when (event) {

            is CategoryAdd.Event.Insert -> {
                if (validateInputs()) {
                    insert()
                }
            }

            is CategoryAdd.Event.Update -> {

            }

            is CategoryAdd.Event.Name -> {
                _uiState.value = _uiState.value.copy(name = event.value)
            }

            is CategoryAdd.Event.GoToBack -> {
                viewModelScope.launch {
                    _navigation.send(CategoryAdd.Navigation.GoToBack)
                }
            }

            is CategoryAdd.Event.Icon -> {
                _uiState.value = _uiState.value.copy(icon = event.value)
                _uiState.value = _uiState.value.copy(shouldShowIconBottomSheet = false)
            }

            CategoryAdd.Event.SelectIcon -> {
                _uiState.value = _uiState.value.copy(shouldShowIconBottomSheet = true)
            }
        }
    }

    private fun validateInputs(): Boolean {
//        val validationResult = validateInputUseCase.execute(InputType, uiState.value.mobile)
//        _uiState.value = _uiState.value.copy(amountError = validationResult.errorMessage)
//        return validationResult.successful
        val name = uiState.value.name
        return name.isNotEmpty()
    }

    private fun insert() {
        val request = CategoryRequest(
            name = uiState.value.name,
            icon = uiState.value.icon,
            color = null
        )
        viewModelScope.launch(Dispatchers.IO) {
            useCase.insert(request).collectLatest { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        val data = response.data as? Boolean ?: return@collectLatest
                        _uiState.update { CategoryAdd.UiState(isAddCompleted = data) }
                        _navigation.send(CategoryAdd.Navigation.GoToCategoryList)
                    }

                    Status.ERROR -> {
                        _uiState.update { CategoryAdd.UiState(error = UiText.DynamicString(response.message.toString())) }
                    }

                    Status.LOADING -> {
                        _uiState.update { CategoryAdd.UiState(isLoading = true) }
                    }
                }
            }
        }
    }

}

object CategoryAdd {

    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        var isAddCompleted: Boolean = false,
        var shouldShowIconBottomSheet: Boolean = false,
        val defaultCategoryIcon: List<Int> = emptyList(),

        val name: String = "",
        val nameError: UiText? = null,
        val icon: Int? = null

    )

    sealed interface Navigation {
        data object GoToCategoryList : Navigation
        data object GoToBack : Navigation
    }

    sealed interface Event {
        data object GoToBack : Event
        data object Insert : Event
        data object Update : Event
        data class Name(val value: String) : Event
        data class Icon(val value: Int) : Event
        data object SelectIcon : Event
    }

}
