package com.srabbijan.design

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.srabbijan.common.utils.convertMillisToDate
import com.srabbijan.design.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerModal(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        colors = DatePickerDefaults.colors().copy(
            titleContentColor = AppTheme.colorScheme.primary,
            headlineContentColor = AppTheme.colorScheme.primary,
            containerColor = AppTheme.colorScheme.background,

            ),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(convertMillisToDate(datePickerState.selectedDateMillis ?: 0))
                onDismiss()
            }) {
                Text(
                    "OK",
                    color = AppTheme.colorScheme.primary,
                    style = AppTheme.typography.labelLarge
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Cancel",
                    color = AppTheme.colorScheme.primary,
                    style = AppTheme.typography.labelLarge
                )
            }
        }
    ) {
        DatePicker(
            colors = DatePickerDefaults.colors().copy(
                titleContentColor = AppTheme.colorScheme.primary,
                headlineContentColor = AppTheme.colorScheme.primary,
                containerColor = AppTheme.colorScheme.background,
            ),
            state = datePickerState
        )
    }
}