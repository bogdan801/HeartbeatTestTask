package com.bogdan801.heartbeat_test_task.presentation.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

fun LocalDate.toFormattedString() =
    "${dayOfMonth.toString().padStart(2, '0')}.${monthNumber.toString().padStart(2, '0')}.$year"

fun LocalDateTime.toFormattedString() =
    "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}, " +
            "${dayOfMonth.toString().padStart(2, '0')}/${monthNumber.toString().padStart(2, '0')}/$year"