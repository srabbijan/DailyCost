package com.srabbijan.expense.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.design.AppToolbarWithBack
import com.srabbijan.design.InputTextField
import com.srabbijan.design.PrimaryButton
import com.srabbijan.design.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ExpenseAddScreen(
    expenseId: String? = null,
    type: String? = null,
    viewModel: ExpenseViewModel,
    navHostController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

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
        topBar = {
            AppToolbarWithBack(
                title = if (expenseId == null) stringResource(R.string.customer_add) else stringResource(R.string.customer_edit)
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
            InputTextField(
                initialValue = uiState.value.amount,
                placeholder = "Tk",
                isError = uiState.value.amountError != null,
                errorMessage = uiState.value.amountError,
                keyboardType = KeyboardType.Number,
            ) {
                viewModel.onEvent(ExpenseAdd.Event.Amount(it))
            }



            PrimaryButton (
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