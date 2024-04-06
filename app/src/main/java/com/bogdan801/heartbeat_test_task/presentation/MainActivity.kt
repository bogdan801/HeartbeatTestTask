package com.bogdan801.heartbeat_test_task.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bogdan801.heartbeat_test_task.presentation.navigation.Navigation
import com.bogdan801.heartbeat_test_task.presentation.screens.add_edit.AddEditScreen
import com.bogdan801.heartbeat_test_task.presentation.screens.history.HistoryScreen
import com.bogdan801.heartbeat_test_task.presentation.screens.home.HomeScreen
import com.bogdan801.heartbeat_test_task.presentation.theme.HeartbeatTestTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeartbeatTestTaskTheme {
                val navController = rememberNavController()
                Navigation(
                    navController = navController,
                    homeScreen = {
                        HomeScreen(
                            navController = navController
                        )
                    },
                    historyScreen = {
                        HistoryScreen(
                            navController = navController
                        )
                    },
                    addEditScreen = {
                        AddEditScreen(
                            navController = navController,
                            editId = it
                        )
                    }
                )
            }
        }
    }
}