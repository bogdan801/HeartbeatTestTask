package com.bogdan801.heartbeat_test_task.data.local_db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val itemID: Int,
    val systolicPressure: String,
    val diastolicPressure: String,
    val pulse: String,
    val datetime: Long
)