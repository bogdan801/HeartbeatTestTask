package com.bogdan801.heartbeat_test_task.presentation.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getCurrentDate(): LocalDate {
    val dateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return LocalDate(dateTime.year, dateTime.monthNumber, dateTime.dayOfMonth)
}

fun getCurrentTime(): LocalTime {
    val dateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return LocalTime(dateTime.hour, dateTime.minute, dateTime.second, dateTime.nanosecond)
}


fun LocalDate.toFormattedString() =
    "${dayOfMonth.toString().padStart(2, '0')}/${monthNumber.toString().padStart(2, '0')}/$year"

fun LocalTime.toFormattedString() =
    "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"

fun LocalDateTime.toFormattedString() =
    "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}, " +
            "${dayOfMonth.toString().padStart(2, '0')}/${monthNumber.toString().padStart(2, '0')}/$year"