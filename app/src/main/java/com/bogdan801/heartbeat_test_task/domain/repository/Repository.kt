package com.bogdan801.heartbeat_test_task.domain.repository

import com.bogdan801.heartbeat_test_task.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface Repository {
    //insert
    suspend fun insertItem(item: Item): Long

    //update
    suspend fun updateItem(item: Item)

    //delete
    suspend fun deleteItem(itemId: Int)

    //delete all
    suspend fun deleteAllItems()

    //select
    fun getItems() : Flow<List<Item>>
    fun getTop3Items() : Flow<List<Item>>
}