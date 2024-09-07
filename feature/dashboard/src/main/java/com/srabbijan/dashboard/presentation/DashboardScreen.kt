package com.srabbijan.dashboard.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.common.utils.TransactionType
import com.srabbijan.common.utils.UiText
import com.srabbijan.common.utils.toCurrencyFormat
import com.srabbijan.design.AppActionButton
import com.srabbijan.design.AppDateIntervalView
import com.srabbijan.design.loadingDialog
import com.srabbijan.design.theme.ColorDisable
import com.srabbijan.design.theme.ColorError
import com.srabbijan.design.theme.ColorSuccess
import com.srabbijan.design.utils.r
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    navHostController: NavHostController
) {

    val uiState = viewModel.uiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                when (it) {
                    Dashboard.Navigation.GoToAddIncome -> {
                        navHostController.navigate(NavigationRoute.ExpenseAdd(TransactionType.CASH_IN.value))
                    }
                    Dashboard.Navigation.GoToAddExpense -> {
                        navHostController.navigate(NavigationRoute.ExpenseAdd(TransactionType.CASH_OUT.value))
                    }
                }
            }
    }

    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {

            Lifecycle.State.RESUMED -> {
                viewModel.onEvent(Dashboard.Event.ThisMonth)
            }

            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
        }
    }

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize(),
//        topBar = { AppToolbarHome {} }
    ) { innerPadding ->

        if (uiState.value.isLoading) {
            loadingDialog { }
        }

        if (uiState.value.error !is UiText.Idle) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.value.error.asString(context))
            }
        }

        Column(modifier = Modifier.padding(innerPadding)) {
            AppDateIntervalView(
                showingDate = viewModel.showingDate.value,
                offset = viewModel.offset.intValue,
                onNext = {
                    viewModel.onEvent(Dashboard.Event.NextInterval)
                },
                onPrevious = {
                    viewModel.onEvent(Dashboard.Event.PreInterval)
                }
            ){
                uiState.value.summaryData?.let { data ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Income")
                        Text(text = data.income.toCurrencyFormat())
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Expense")
                        Text(text = data.expense.toCurrencyFormat())
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Balance")
                        Text(text = data.balance.toCurrencyFormat())
                    }
                }
            }


            Spacer(modifier = Modifier.height(12.r()))
            // Dokan Feature
            LazyColumn (
                modifier = Modifier.weight(1f),
            ) {
                items(uiState.value.expenseList) { data ->
                    Text("${data.amount} ${data.type}")
                }
            }

            Box {
                val brush = Brush.verticalGradient(listOf(Color.Transparent, ColorDisable))
                Canvas(
                    modifier = Modifier.fillMaxWidth()
                        .height(24.dp),
                    onDraw = {
                        drawRoundRect(
                            brush
                        )
                    }
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = CardColors(
                        contentColor = Color.Black,
                        containerColor = Color.White,
                        disabledContainerColor = ColorDisable,
                        disabledContentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppActionButton(
                            btnName = "Cash In",
                            bgColor = ColorSuccess,
                            textColor = Color.White,
                            modifier = Modifier.weight(1f),
                        ) {
                            viewModel.onEvent(event = Dashboard.Event.AddIncome)
                        }
                        AppActionButton(
                            btnName = "Cash Out",
                            bgColor = ColorError,
                            textColor = Color.White,
                            modifier = Modifier.weight(1f),
                        ) {
                            viewModel.onEvent(event = Dashboard.Event.AddExpense)
                        }
                    }
                }
            }
        }
    }
}
