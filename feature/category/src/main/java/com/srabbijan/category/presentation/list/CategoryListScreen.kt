package com.srabbijan.category.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.design.AppToolbarWithBack
import com.srabbijan.design.LoadingDialog
import com.srabbijan.design.R
import com.srabbijan.design.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CategoryListScreen(
    viewModel: CategoryListViewModel,
    navHostController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {

            Lifecycle.State.RESUMED -> {
                viewModel.onEvent(CategoryList.Event.InitialDataFetch)
            }

            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
        }
    }
    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                when (it) {
                    CategoryList.Navigation.GoToBack -> {
                        navHostController.navigateUp()
                    }

                    CategoryList.Navigation.GoToCategoryAdd -> {
                        navHostController.navigate(NavigationRoute.CategoryAdd)
                    }
                }
            }
    }

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(event = CategoryList.Event.GoToCategoryAdd)
                },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        topBar = {
            AppToolbarWithBack(
                label = "Category List"
            ) {
                viewModel.onEvent(CategoryList.Event.GoToBack)
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


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            items(uiState.value.categoryList) { data ->
                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(36.dp),
                            painter = painterResource(id = data.icon?: R.drawable.category),
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = data.name, style = AppTheme.typography.paragraph)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Edit",
                            style = AppTheme.typography.titleNormal,
                            color = AppTheme.colorScheme.primary
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 4.dp),
                        thickness = 1.dp,
                        color = AppTheme.colorScheme.separator
                    )
                }

            }
        }
    }
}