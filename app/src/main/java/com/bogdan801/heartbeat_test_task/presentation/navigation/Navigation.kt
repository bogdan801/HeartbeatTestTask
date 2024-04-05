package com.bogdan801.heartbeat_test_task.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    homeScreen: @Composable () -> Unit,
    historyScreen: @Composable () -> Unit,
    addEditScreen: @Composable (id: Int?) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(
            route = Screen.Home.route
        ){
            homeScreen()
        }

        composable(
            route = Screen.History.route
        ){
            historyScreen()
        }

        composable(
            route = Screen.AddEdit.route
        ){
            addEditScreen(null)
        }

        composable(
            route = Screen.AddEdit.route + "/{itemID}",
            arguments = listOf(
                navArgument("itemID"){
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments!!.getInt("itemID")
            addEditScreen(if(id<0) null else id)
        }
    }
}