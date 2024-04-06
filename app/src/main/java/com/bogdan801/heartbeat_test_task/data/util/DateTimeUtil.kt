package com.bogdan801.heartbeat_test_task.data.util

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault

fun getCurrentDateTime(): LocalDateTime =
    Clock.System.now().toLocalDateTime(currentSystemDefault())

fun LocalDateTime.toEpoch() = toInstant(currentSystemDefault()).toEpochMilliseconds()

fun Long.toLocalDateTime() =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(currentSystemDefault())