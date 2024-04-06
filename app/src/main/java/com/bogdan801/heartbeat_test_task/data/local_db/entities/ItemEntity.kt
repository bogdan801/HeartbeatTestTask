package com.bogdan801.heartbeat_test_task.data.local_db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan801.heartbeat_test_task.data.util.toLocalDateTime
import com.bogdan801.heartbeat_test_task.domain.model.Item

@Entity
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val itemID: Int,
    val systolicPressure: Int,
    val diastolicPressure: Int,
    val pulse: Int,
    val datetime: Long
){
    fun toItem() = Item(
        itemID = itemID,
        systolicPressure = systolicPressure,
        diastolicPressure = diastolicPressure,
        pulse = pulse,
        datetime = datetime.toLocalDateTime()
    )
}