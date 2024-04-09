package com.bogdan801.heartbeat_test_task.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bogdan801.heartbeat_test_task.presentation.components.ActionButton
import com.bogdan801.heartbeat_test_task.presentation.components.ItemCard
import com.bogdan801.heartbeat_test_task.presentation.navigation.Screen
import com.bogdan801.heartbeat_test_task.presentation.util.DeviceOrientation
import com.bogdan801.heartbeat_test_task.presentation.util.getDeviceConfiguration
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
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
                        text = "Blood pressure BPM Tracker",
                        style = MaterialTheme.typography.displaySmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEdit.route) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { systemPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(systemPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            if(screenState.displayItems.isNotEmpty()){
                val configuration = getDeviceConfiguration(LocalConfiguration.current)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(top = 8.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(screenState.displayItems) { item ->
                        ItemCard(
                            modifier = Modifier.animateItemPlacement(),
                            height = if(configuration.orientation == DeviceOrientation.Landscape) 72.dp else 96.dp,
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
                    item {
                        ActionButton(
                            modifier = Modifier
                                .width(500.dp)
                                .height(56.dp)
                                .animateItemPlacement(),
                            label = "All History",
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                            icon = {
                                Icon(imageVector = Icons.Outlined.Restore, contentDescription = "")
                            },
                            onClick = {
                                navController.navigate(Screen.History.route)
                            }
                        )
                    }
                }
            }
            else {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Records list is empty.\nPress \"+\" to add new records",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}