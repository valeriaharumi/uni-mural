package com.github.valeriaharumi.challenge.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

sealed class SnackbarType {
    object Success : SnackbarType()
    object Info : SnackbarType()
    object Error : SnackbarType()
}

fun Int.secondsToMillis(): Int {
    return this * 1000
}

@Composable
fun SnackbarByType(
    message: String,
    type: SnackbarType,
    duration: Int  = 3,
    onDismiss: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val snackbarBackgroundColor = when (type) {
        SnackbarType.Success -> Color.Green
        SnackbarType.Info -> Color.Yellow
        SnackbarType.Error -> Color.Red
    }

    val snackbarContentColor = if (type == SnackbarType.Error) Color.White else Color.Black
    var showSnackbar by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
        content = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        IconButton(onClick = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Fechar")
                        }
                    },
                    content = {
                        Text(
                            message,
                            color = snackbarContentColor,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    },
                    backgroundColor = snackbarBackgroundColor,
                )
            }
        }
    )

    LaunchedEffect(Unit) {
        snackbarHostState.showSnackbar(message)
        delay(duration * 1000L)
        snackbarHostState.currentSnackbarData?.dismiss()
        showSnackbar = false
        onDismiss()
    }
}