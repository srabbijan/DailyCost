package com.srabbijan.dashboard.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.srabbijan.common.utils.UiText
import com.srabbijan.common.utils.toCurrencyFormat
import com.srabbijan.design.AppDateIntervalView
import com.srabbijan.design.AppToolbarHome
import com.srabbijan.design.LoadingDialog
import com.srabbijan.design.theme.AppTheme
import com.srabbijan.design.utils.r

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

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

            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                items(uiState.value.expenseList) { data ->
                    Text("${data.amount} ${data.type}")
                }
            }

        }
    }
}
