package com.srabbijan.expense.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.srabbijan.category.presentation.add.CategoryAdd
import com.srabbijan.category.presentation.add.CategoryIconSelectBottomSheet
import com.srabbijan.category.presentation.add.CategorySelectBottomSheet
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.common.utils.TransactionType
import com.srabbijan.design.AppOutLineCard
import com.srabbijan.design.AppToolbarWithBack
import com.srabbijan.design.InputTextField
import com.srabbijan.design.PrimaryButton
import com.srabbijan.design.R
import com.srabbijan.design.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ExpenseAddScreen(
    expenseId: String? = null,
    viewModel: ExpenseViewModel,
    navHostController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val showCategorySelectBottomSheet = remember { mutableStateOf(false) }
    /*    LaunchedEffect(key1 = true) {
            expenseId?.let {
                viewModel.onEvent(ExpenseAdd.Event.FetchCustomerById(it))
            }
        }*/
    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                when (it) {
                    ExpenseAdd.Navigation.GoToBack -> {
                        navHostController.navigateUp()
                    }

                    ExpenseAdd.Navigation.GoToDashboard -> {
                        navHostController.navigate(NavigationRoute.Dashboard) {
                            popUpTo(NavigationRoute.Dashboard) { inclusive = true }
                        }
                    }
                }
            }
    }

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize(),
        containerColor = AppTheme.colorScheme.primaryContainer,
        topBar = {
            AppToolbarWithBack(
                label = if (expenseId == null) "Add new" else "Edit"
            ) {
                viewModel.onEvent(ExpenseAdd.Event.GoToBack)
            }
        },

        ) { innerPadding ->

//        if (uiState.value.error !is UiText.Idle) {
//            ErrorDialog(
//                message = uiState.value.error.asString(context = context)
//            ) {
//                viewModel.onEvent(CustomerAdd.Event.CloseErrorDialog)
//            }
//        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            var selectedIndex by remember { mutableIntStateOf(0) }
            val options = listOf("Cash Out", "Cash In")

            SingleChoiceSegmentedButtonRow {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        colors = SegmentedButtonDefaults.colors().copy(
                            activeContainerColor = AppTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                            activeContentColor = AppTheme.colorScheme.onPrimaryContainer,
                            inactiveContainerColor = AppTheme.colorScheme.primaryContainer,
                        ),
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        onClick = {
                            selectedIndex = index

                            when (index) {
                                0 -> {
                                    viewModel.onEvent(ExpenseAdd.Event.Type(TransactionType.CASH_OUT.value))
                                }

                                1 -> {
                                    viewModel.onEvent(ExpenseAdd.Event.Type(TransactionType.CASH_IN.value))
                                }
                            }
                        },

                        selected = index == selectedIndex
                    ) {
                        Text(label)
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
            AppOutLineCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showCategorySelectBottomSheet.value =
                                !showCategorySelectBottomSheet.value
                            viewModel.onEvent(ExpenseAdd.Event.SelectCategory)
                        }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = uiState.value.category?.name ?: "Select Category")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }

            if (uiState.value.shouldShowCategoryBottomSheet) {
                CategorySelectBottomSheet(
                    showModalBottomSheet = showCategorySelectBottomSheet,
                    title = "Choose Category icon",
                    data = uiState.value.categoryList.toList()
                ) { selectedIcon ->
                    viewModel.onEvent(ExpenseAdd.Event.Category(selectedIcon))
                }
            }
            InputTextField(
                initialValue = uiState.value.amount,
                placeholder = "Tk",
                isError = uiState.value.amountError != null,
                errorMessage = uiState.value.amountError,
                keyboardType = KeyboardType.Number,
            ) {
                viewModel.onEvent(ExpenseAdd.Event.Amount(it))
            }

            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                label = if (expenseId == null) stringResource(R.string.save) else stringResource(R.string.update)
            ) {
                if (expenseId == null)
                    viewModel.onEvent(ExpenseAdd.Event.Insert)
                else
                    viewModel.onEvent(ExpenseAdd.Event.Update)

            }
        }

    }
}