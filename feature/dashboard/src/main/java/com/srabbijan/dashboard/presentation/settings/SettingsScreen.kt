package com.srabbijan.dashboard.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.design.AppToolbarHome
import com.srabbijan.design.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navHostController: NavHostController
) {

    val uiState = viewModel.uiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                when (it) {
                    Settings.Navigation.GoToManageCategory -> {
                        navHostController.navigate(NavigationRoute.ExpenseAdd)
                    }
                }
            }
    }


    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize(),
        containerColor = AppTheme.colorScheme.background,
        topBar = { AppToolbarHome("Settings") },
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {

            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                items(uiState.value.settingsOptionList) { data ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(event = Settings.Event.SettingOptionNavigation(data.route))
                            },
                    ) {
                        Image(
                            painter = painterResource(data.icon),
                            contentDescription = null,
                        )
                        Text(data.name)
                        Icon(
                            imageVector = Icons.Outlined.ArrowCircleRight,
                            contentDescription = null,
                        )
                    }
                }
            }

        }
    }
}
