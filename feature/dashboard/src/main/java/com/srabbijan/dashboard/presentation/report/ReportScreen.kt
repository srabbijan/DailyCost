package com.srabbijan.dashboard.presentation.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.common.utils.TransactionType
import com.srabbijan.common.utils.toCurrencyFormat
import com.srabbijan.dashboard.presentation.settings.Settings
import com.srabbijan.design.AppDateIntervalView
import com.srabbijan.design.AppToolbarHome
import com.srabbijan.design.theme.AppTheme
import com.srabbijan.design.theme.success
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ReportScreen(
    viewModel: ReportViewModel,
    navHostController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                when (it) {
                    is Report.Navigation.GoToPdfExport -> {
                        navHostController.navigate(NavigationRoute.PdfExport(it.data))
                    }
                }
            }
    }
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {

            Lifecycle.State.RESUMED -> {
                viewModel.onEvent(Report.Event.ThisMonth)
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
        topBar = {
            AppToolbarHome("Reports", actions = {
                IconButton(
                    onClick = {
                        viewModel.onEvent(Report.Event.ExportPdf)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FileDownload,
                        contentDescription = null
                    )
                }
            })
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            AppDateIntervalView(
                showingDate = viewModel.showingDate.value,
                offset = viewModel.offset.intValue,
                onNext = {
                    viewModel.onEvent(Report.Event.NextInterval)
                },
                onPrevious = {
                    viewModel.onEvent(Report.Event.PreInterval)
                }
            ) {
            }

            LazyColumn {
                items(uiState.value.reportList) { data ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(
                                    data.categoryIcon ?: com.srabbijan.design.R.drawable.category
                                ),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    data.categoryName ?: "",
                                    style = AppTheme.typography.paragraph
                                )
                                Text(
                                    "${data.transactions} Transactions",
                                    style = AppTheme.typography.labelSmall
                                )
                            }

                        }
                        Text(
                            data.amount.toCurrencyFormat(),
                            style = AppTheme.typography.paragraph,
                            color = if (data.type == TransactionType.CASH_IN.value) success else com.srabbijan.design.theme.error
                        )
                    }
                }
            }

        }
    }
}
