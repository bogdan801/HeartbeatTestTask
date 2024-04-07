package com.bogdan801.heartbeat_test_task.presentation.screens.add_edit

import com.bogdan801.heartbeat_test_task.presentation.util.getCurrentDate
import com.bogdan801.heartbeat_test_task.presentation.util.getCurrentTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class AddEditScreenState(
    val systolicPressure: Int = 120,
    val diastolicPressure: Int = 80,
    val pulse: Int = 70,
    val date: LocalDate = getCurrentDate(),
    val time: LocalTime = getCurrentTime()
)
