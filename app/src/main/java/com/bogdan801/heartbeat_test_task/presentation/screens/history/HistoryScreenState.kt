package com.bogdan801.heartbeat_test_task.presentation.screens.history

import com.bogdan801.heartbeat_test_task.domain.model.Item

data class HistoryScreenState(
    val displayItems: List<Item> = listOf(),
    val deletedItem: Item? = null
)
