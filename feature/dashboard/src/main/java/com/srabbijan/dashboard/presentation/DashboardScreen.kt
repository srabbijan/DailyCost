package com.srabbijan.dashboard.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.srabbijan.design.AppDateIntervalView
import com.srabbijan.design.AppNavigationBar
import com.srabbijan.design.AppNavigationItem
import com.srabbijan.design.AppToolbarHome
import com.srabbijan.design.LoadingDialog
import com.srabbijan.design.PrimaryButton
import com.srabbijan.design.theme.AppTheme
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
        containerColor = AppTheme.colorScheme.background,
        topBar = { AppToolbarHome() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        bottomBar = {
            AppNavigationBar(
                listOf(
                    AppNavigationItem(
                        label = "Home",
                        icon = Icons.Outlined.Home,
                        route = NavigationRoute.Home
                    ),
                    AppNavigationItem(
                        label = "Report",
                        icon = Icons.Outlined.Receipt,
                        route = NavigationRoute.Report
                    )
                )
            ) { }
        }
    ) { innerPadding ->

        if (uiState.value.isLoading) {
            LoadingDialog { }
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
            ) {
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
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                items(uiState.value.expenseList) { data ->
                    Text("${data.amount} ${data.type}")
                }
            }


            Box {
                val brush = Brush.verticalGradient(listOf(Color.Transparent, Color.LightGray))
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
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
                        disabledContainerColor = Color.LightGray,
                        disabledContentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PrimaryButton(
                            label = "Cash In",
                            modifier = Modifier.weight(1f),
                        ) {
                            viewModel.onEvent(event = Dashboard.Event.AddIncome)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        PrimaryButton(
                            label = "Cash Out",
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
