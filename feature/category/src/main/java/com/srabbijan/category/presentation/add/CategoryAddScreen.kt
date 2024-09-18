package com.srabbijan.category.presentation.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.srabbijan.common.navigation.NavigationRoute
import com.srabbijan.design.AppToolbarWithBack
import com.srabbijan.design.InputTextField
import com.srabbijan.design.PrimaryButton
import com.srabbijan.design.R
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CategoryAddScreen(
    categoryId: String? = null,
    viewModel: CategoryAddViewModel,
    navHostController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val showCategoryIconSelectBottomSheet = remember { mutableStateOf(false) }

    /*    LaunchedEffect(key1 = true) {
            expenseId?.let {
                viewModel.onEvent(ExpenseAdd.Event.FetchCustomerById(it))
            }
        }*/
    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                when (it) {
                    CategoryAdd.Navigation.GoToBack -> {
                        navHostController.navigateUp()
                    }

                    CategoryAdd.Navigation.GoToCategoryList -> {
                        navHostController.navigate(NavigationRoute.Category) {
                            popUpTo(NavigationRoute.Category) { inclusive = true }
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
                label = if (categoryId == null) "Add new category" else "Edit category"
            ) {
                viewModel.onEvent(CategoryAdd.Event.GoToBack)
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
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = uiState.value.icon ?: R.drawable.ic_icon_add_placeholder),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        showCategoryIconSelectBottomSheet.value =
                            !showCategoryIconSelectBottomSheet.value
                        viewModel.onEvent(CategoryAdd.Event.SelectIcon)
                    }
                )
                if (uiState.value.shouldShowIconBottomSheet){
                    CategoryIconSelectBottomSheet(
                        showModalBottomSheet = showCategoryIconSelectBottomSheet,
                        title = "Choose Category icon",
                        data = uiState.value.defaultCategoryIcon.toList()
                    ) { selectedIcon ->
                        viewModel.onEvent(CategoryAdd.Event.Icon(selectedIcon))
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                InputTextField(
                    initialValue = uiState.value.name,
                    placeholder = "Category name",
                    isError = uiState.value.nameError != null,
                    errorMessage = uiState.value.nameError,
                    keyboardType = KeyboardType.Text,
                ) {
                    viewModel.onEvent(CategoryAdd.Event.Name(it))
                }
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )
            PrimaryButton(
                label = if (categoryId == null) stringResource(R.string.save) else stringResource(R.string.update)
            ) {
                if (categoryId == null)
                    viewModel.onEvent(CategoryAdd.Event.Insert)
                else
                    viewModel.onEvent(CategoryAdd.Event.Update)
            }
        }

    }
}