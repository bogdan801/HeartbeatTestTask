package com.bogdan801.heartbeat_test_task.data.repository

import com.bogdan801.heartbeat_test_task.data.local_db.Dao
import com.bogdan801.heartbeat_test_task.data.local_db.toItemEntity
import com.bogdan801.heartbeat_test_task.domain.model.Item
import com.bogdan801.heartbeat_test_task.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val dao: Dao
) : Repository {
    override suspend fun insertItem(item: Item): Long = dao.insertItemEntity(item.toItemEntity())

    override suspend fun updateItem(item: Item) {
        dao.updateItemEntity(item.toItemEntity())
    }

    override suspend fun deleteItem(itemId: Int) {
        dao.deleteItemEntity(itemId)
    }

    override suspend fun deleteAllItems() {
        dao.deleteAllItemEntities()
    }

    override fun getItems(): Flow<List<Item>> = flow {
        dao.getItemEntities().collect{ itemEntities ->
            emit(itemEntities.map { it.toItem() })
        }
    }

    override fun getTop3Items(): Flow<List<Item>> = flow {
        dao.getTop3ItemEntities().collect{ itemEntities ->
            emit(itemEntities.map { it.toItem() })
        }
    }
}