package com.srabbijan.category.presentation.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.srabbijan.database.entity.CategoryTable
import com.srabbijan.design.AppHorizontalDivider
import com.srabbijan.design.R
import com.srabbijan.design.theme.AppTheme
import com.srabbijan.design.utils.r
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryIconSelectBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    data: List<Int>,
    title: String,
    onItemClick: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val skipPartially by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartially)

    if (showModalBottomSheet.value)
        ModalBottomSheet(
            onDismissRequest = { showModalBottomSheet.value = false },
            sheetState = bottomSheetState,
            containerColor = AppTheme.colorScheme.primaryContainer
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.r()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title, style = AppTheme.typography.paragraph)
                AppHorizontalDivider()

                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    columns = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(data) { item ->
                        Image(
                            painter = painterResource(item),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    scope
                                        .launch { bottomSheetState.hide() }
                                        .invokeOnCompletion {
                                            if (!bottomSheetState.isVisible) {
                                                showModalBottomSheet.value = false
                                                onItemClick.invoke(item)
                                            }
                                        }

                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    data: List<CategoryTable>,
    title: String,
    onItemClick: (CategoryTable) -> Unit
) {
    val scope = rememberCoroutineScope()
    val skipPartially by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartially)

    if (showModalBottomSheet.value)
        ModalBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { showModalBottomSheet.value = false },
            sheetState = bottomSheetState,
            containerColor = AppTheme.colorScheme.primaryContainer
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.r()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title, style = AppTheme.typography.paragraph)
                AppHorizontalDivider()

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    items(data) { item ->
                        Column(
                            modifier = Modifier
                                .clickable {
                                    scope
                                        .launch { bottomSheetState.hide() }
                                        .invokeOnCompletion {
                                            if (!bottomSheetState.isVisible) {
                                                showModalBottomSheet.value = false
                                                onItemClick.invoke(item)
                                            }
                                        }
                                }
                                .padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(item.icon ?: R.drawable.category),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(item.name, style = AppTheme.typography.labelNormal)
                        }

                    }
                }
            }
        }
}
