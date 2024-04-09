package com.bogdan801.heartbeat_test_task.presentation.screens.history

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bogdan801.heartbeat_test_task.presentation.components.ActionButton
import com.bogdan801.heartbeat_test_task.presentation.components.ItemCard
import com.bogdan801.heartbeat_test_task.presentation.components.WarningDialogBox
import com.bogdan801.heartbeat_test_task.presentation.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        modifier = if(screenState.displayItems.isNotEmpty())
                            Modifier.offset(y = (-88).dp)
                        else Modifier,
                        snackbarData = data,
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        actionColor = MaterialTheme.colorScheme.primary
                    )
                }
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "History",
                        style = MaterialTheme.typography.displaySmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
                    }
                }
            )
        }
    ) { systemPadding ->
        var showDialog by rememberSaveable { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(systemPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            if(screenState.displayItems.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 88.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(screenState.displayItems) { item ->
                        ItemCard(
                            modifier = Modifier.animateItemPlacement(),
                            item = item,
                            onEditItemClick = {
                                navController.navigate(Screen.AddEdit.withArgs("$it"))
                            },
                            onDeleteItemClick = {
                                viewModel.deleteItem(it)

                                scope.launch {
                                    snackbarHostState.currentSnackbarData?.dismiss()

                                    val result = snackbarHostState.showSnackbar(
                                        message = "Item has been deleted",
                                        actionLabel = "RESTORE",
                                        duration = SnackbarDuration.Short
                                    )
                                    when (result) {
                                        SnackbarResult.ActionPerformed -> {
                                            viewModel.restoreItem()
                                        }
                                        SnackbarResult.Dismissed -> {}
                                    }
                                }
                            }
                        )
                    }
                }

                ActionButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(450.dp)
                        .height(56.dp)
                        .align(Alignment.BottomCenter),
                    label = "Clear History",
                    backgroundColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    icon = {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                    },
                    onClick = {
                        showDialog = true
                    }
                )
            }
            else {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "History is empty",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }


        WarningDialogBox(
            text = "Are you sure you want to clear the history?",
            showDialog = showDialog,
            onConfirm = {
                viewModel.clearHistory()
                Toast.makeText(context, "History has been cleared", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}