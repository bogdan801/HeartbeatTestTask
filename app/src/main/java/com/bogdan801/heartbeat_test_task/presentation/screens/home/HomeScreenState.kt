package com.bogdan801.heartbeat_test_task.presentation.screens.home

import com.bogdan801.heartbeat_test_task.domain.model.Item

data class HomeScreenState(
    val displayItems: List<Item> = listOf(),
    val deletedItem: Item? = null
)
