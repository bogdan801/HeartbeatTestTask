package com.bogdan801.heartbeat_test_task.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
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
    Surface(modifier = modifier) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = Screen.Home.route
        ){
            composable(
                route = Screen.Home.route,
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right
                    )
                }
            ){
                homeScreen()
            }

            composable(
                route = Screen.History.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(200)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right
                    )
                }
            ){
                historyScreen()
            }

            composable(
                route = Screen.AddEdit.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right
                    )
                }
            ){
                addEditScreen(null)
            }

            composable(
                route = Screen.AddEdit.route + "/{itemID}",
                arguments = listOf(
                    navArgument("itemID"){
                        type = NavType.IntType
                    }
                ),
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right
                    )
                }
            ){
                val id = it.arguments!!.getInt("itemID")
                addEditScreen(if(id<0) null else id)
            }
        }

    }
}