package com.bogdan801.heartbeat_test_task.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object History : Screen("history")
    data object AddEdit : Screen("add_edit")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}