package com.srabbijan.expense.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.srabbijan.category.presentation.add.CategorySelectBottomSheet
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.common.utils.FORMAT_yyyy_MM_dd
import com.srabbijan.common.utils.TransactionType
import com.srabbijan.common.utils.toUiTime
import com.srabbijan.design.AppDatePickerModal
import com.srabbijan.design.AppOutLineCard
import com.srabbijan.design.AppToolbarWithBack
import com.srabbijan.design.DateText
import com.srabbijan.design.InputTextField
import com.srabbijan.design.LoadingDialog
import com.srabbijan.design.PrimaryButton
import com.srabbijan.design.R
import com.srabbijan.design.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ExpenseAddScreen(
    expenseId: Long? = null,
    viewModel: ExpenseViewModel,
    navHostController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val showCategorySelectBottomSheet = remember { mutableStateOf(false) }
    val showDatePickerDialog = remember { mutableStateOf(false) }
        LaunchedEffect(key1 = true) {
            expenseId?.let {
                viewModel.onEvent(ExpenseAdd.Event.FetchExpenseById(it))
            }
        }
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

        if (uiState.value.isLoading) {
            LoadingDialog { }
        }

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
            var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
            val options = listOf("Cash Out", "Cash In")

            Row (
                modifier = Modifier.padding(horizontal = 12.dp)
            ){
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
                            Text(label, style = AppTheme.typography.labelNormal)
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                DateText(uiState.value.date.toUiTime(outFormat = FORMAT_yyyy_MM_dd)){
                    showDatePickerDialog.value = true
                }
            }
            if ( showDatePickerDialog.value) {
                AppDatePickerModal(
                    onDismiss = {
                        showDatePickerDialog.value = false
                    },
                    onDateSelected = {
                        viewModel.onEvent(ExpenseAdd.Event.Date(it))
                    }
                )
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
                    Text(text = uiState.value.category?.name ?: "Select Category", style = AppTheme.typography.labelNormal)
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
                keyboardType = KeyboardType.Decimal,
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