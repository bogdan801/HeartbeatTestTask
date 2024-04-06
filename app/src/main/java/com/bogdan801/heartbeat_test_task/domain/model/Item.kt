package com.bogdan801.heartbeat_test_task.domain.model

import kotlinx.datetime.LocalDateTime

data class Item(
    val itemID: Int = 0,
    val systolicPressure: Int = 100,
    val diastolicPressure: Int = 78,
    val pulse: Int = 80,
    val datetime: LocalDateTime = LocalDateTime(2002,4,26, 12,4)
)