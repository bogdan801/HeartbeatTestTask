package com.bogdan801.heartbeat_test_task.data.local_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bogdan801.heartbeat_test_task.data.local_db.entities.ItemEntity

@Database(
    entities = [ItemEntity::class],
    exportSchema = true,
    version = 1
)
abstract class Database : RoomDatabase(){
    abstract val dbDao: Dao
}