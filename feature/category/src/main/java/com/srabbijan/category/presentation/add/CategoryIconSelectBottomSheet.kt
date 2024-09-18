package com.srabbijan.category.presentation.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.srabbijan.design.AppHorizontalDivider
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
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.r()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title)
                AppHorizontalDivider()

                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(data) { item ->
                        Image(
                            painter = painterResource(item),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        showModalBottomSheet.value = false
                                        onItemClick.invoke(item)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
}


