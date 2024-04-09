package com.bogdan801.heartbeat_test_task.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun WarningDialogBox(
    showDialog: Boolean = false,
    text: String = "",
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    if(showDialog){
        AlertDialog(
            onDismissRequest = onDismiss,
            shape = RoundedCornerShape(5.dp),
            containerColor = MaterialTheme.colorScheme.background,
            text = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("CONFIRM")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("DISMISS")
                }
            }
        )
    }
}