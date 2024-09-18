package com.srabbijan.dashboard.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.common.utils.TransactionType
import com.srabbijan.common.utils.UiText
import com.srabbijan.common.utils.toCurrencyFormat
import com.srabbijan.common.utils.toUiTime
import com.srabbijan.design.AppDateIntervalView
import com.srabbijan.design.AppToolbarHome
import com.srabbijan.design.LoadingDialog
import com.srabbijan.design.R
import com.srabbijan.design.theme.AppTheme
import com.srabbijan.design.theme.error
import com.srabbijan.design.theme.success
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navHostController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                when (it) {
                    Home.Navigation.GoToAddExpense -> {
                        navHostController.navigate(NavigationRoute.ExpenseAdd)
                    }
                }
            }
    }
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {

            Lifecycle.State.RESUMED -> {
                viewModel.onEvent(Home.Event.ThisMonth)
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
                onClick = {
                    viewModel.onEvent(event = Home.Event.AddExpense)
                },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
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
                    viewModel.onEvent(Home.Event.NextInterval)
                },
                onPrevious = {
                    viewModel.onEvent(Home.Event.PreInterval)
                }
            ) {
                uiState.value.summaryData?.let { data ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Income", style = AppTheme.typography.labelLarge, color = success)
                        Text(
                            text = data.income.toCurrencyFormat(),
                            style = AppTheme.typography.paragraph,
                            color = success
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Expense", style = AppTheme.typography.labelLarge, color = error)
                        Text(
                            text = data.expense.toCurrencyFormat(),
                            style = AppTheme.typography.paragraph,
                            color = error
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Balance", style = AppTheme.typography.labelLarge)
                        Text(
                            text = data.balance.toCurrencyFormat(),
                            style = AppTheme.typography.paragraph
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                items(uiState.value.expenseList) { data ->
                    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(
                                    id = data.categoryIcon ?: R.drawable.category
                                ),
                                modifier = Modifier.size(32.dp),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Column {
                                Text(text = data.categoryName?:"Others", style = AppTheme.typography.paragraph)
                                Text(text = data.date.toUiTime())
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = data.amount.toCurrencyFormat(),
                                style = AppTheme.typography.titleNormal,
                                color = if (data.type == TransactionType.CASH_IN.value) success else error
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 6.dp),
                            thickness = 1.dp,
                            color = AppTheme.colorScheme.separator
                        )
                    }

                }
            }

        }
    }
}
