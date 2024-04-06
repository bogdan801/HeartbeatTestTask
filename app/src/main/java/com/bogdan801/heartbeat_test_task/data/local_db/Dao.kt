package com.bogdan801.heartbeat_test_task.data.local_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bogdan801.heartbeat_test_task.data.local_db.entities.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemEntity(itemEntity: ItemEntity): Long

    //update
    @Update
    suspend fun updateItemEntity(itemEntity: ItemEntity)

    //delete
    @Query("DELETE FROM itementity WHERE itemID == :itemId")
    suspend fun deleteItemEntity(itemId: Int)


    //delete all
    @Query("DELETE FROM itementity")
    suspend fun deleteAllItemEntities()


    //select
    @Query("SELECT * FROM itementity")
    fun getItemEntities() : Flow<List<ItemEntity>>

    @Query("SELECT * FROM ( SELECT * FROM itementity ORDER BY itemID DESC LIMIT 3 )")
    fun getTop3ItemEntities() : Flow<List<ItemEntity>>
}