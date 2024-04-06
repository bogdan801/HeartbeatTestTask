package com.bogdan801.heartbeat_test_task.data.local_db

import com.bogdan801.heartbeat_test_task.data.local_db.entities.ItemEntity
import com.bogdan801.heartbeat_test_task.data.util.toEpoch
import com.bogdan801.heartbeat_test_task.domain.model.Item

fun Item.toItemEntity() = ItemEntity(
    itemID = itemID,
    systolicPressure = systolicPressure,
    diastolicPressure = diastolicPressure,
    pulse = pulse,
    datetime = datetime.toEpoch()
)