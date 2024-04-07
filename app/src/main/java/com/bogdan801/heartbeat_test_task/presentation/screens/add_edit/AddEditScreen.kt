package com.bogdan801.heartbeat_test_task.presentation.screens.add_edit

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bogdan801.heartbeat_test_task.presentation.components.ActionButton
import com.bogdan801.heartbeat_test_task.presentation.components.AdaptiveLayout
import com.bogdan801.heartbeat_test_task.presentation.components.NumberSelector
import com.bogdan801.heartbeat_test_task.presentation.util.getDeviceConfiguration
import com.bogdan801.heartbeat_test_task.presentation.util.toFormattedString
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: AddEditViewModel = hiltViewModel(),
    editId: Int? = null
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        if(editId!= null){
            viewModel.setUpDefaultsToEdit(editId)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if(editId == null) "New Record" else "Edit Record",
                        style = MaterialTheme.typography.displaySmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
                    }
                }
            )
        }
    ) { systemPadding ->
        val configuration = getDeviceConfiguration(LocalConfiguration.current)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(systemPadding)
        ) {
            AdaptiveLayout(
                modifier = Modifier.fillMaxSize(),
                orientation = configuration.orientation,
                portraitRatio = 0.6f,
                firstHalf = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp, horizontal = 20.dp)
                    ) {
                        Text(text = "Measurements", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .width(320.dp)
                                .fillMaxHeight()
                                .align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            NumberSelector(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                title = "Systolic",
                                subtitle = "(mmHg)",
                                minValue = 80,
                                maxValue = 200,
                                currentValue = screenState.systolicPressure,
                                onValueChanged = {
                                    viewModel.setSystolicPressure(it)
                                }
                            )
                            NumberSelector(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                title = "Diastolic",
                                subtitle = "(mmHg)",
                                minValue = 50,
                                maxValue = 150,
                                currentValue = screenState.diastolicPressure,
                                onValueChanged = {
                                    viewModel.setDiastolicPressure(it)
                                }
                            )
                            NumberSelector(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                title = "Pulse",
                                subtitle = "(BPM)",
                                minValue = 0,
                                maxValue = 220,
                                currentValue = screenState.pulse,
                                onValueChanged = {
                                    viewModel.setPulse(it)
                                }
                            )
                        }
                    }
                },
                secondHalf = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp, horizontal = 20.dp)
                    ) {
                        Text(text = "Date & Time", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val dateDialogState = rememberMaterialDialogState()
                            val timeDialogState = rememberMaterialDialogState()

                            ActionButton(
                                modifier = Modifier
                                    .height(48.dp)
                                    .weight(1f),
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                                label = screenState.date.toFormattedString(),
                                labelStyle = MaterialTheme.typography.bodyMedium,
                                contentArrangement = Arrangement.Start,
                                elevation = 1.dp,
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.CalendarToday,
                                        contentDescription = "Select Date"
                                    )
                                },
                                onClick = {
                                    dateDialogState.show()
                                }
                            )
                            ActionButton(
                                modifier = Modifier
                                    .height(48.dp)
                                    .weight(1f),
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                                label = screenState.time.toFormattedString(),
                                labelStyle = MaterialTheme.typography.bodyMedium,
                                contentArrangement = Arrangement.Start,
                                elevation = 1.dp,
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.Schedule,
                                        contentDescription = "Select Time"
                                    )
                                },
                                onClick = {
                                    timeDialogState.show()
                                }
                            )
                            MaterialDialog(
                                dialogState = dateDialogState,
                                backgroundColor = MaterialTheme.colorScheme.background,
                                buttons = {
                                    positiveButton(
                                        text = "Ok",
                                        textStyle = MaterialTheme.typography.bodyMedium
                                            .copy(
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                    )
                                    negativeButton(
                                        text = "Cancel",
                                        textStyle = MaterialTheme.typography.bodyMedium
                                            .copy(
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                    )
                                }
                            ) {
                                datepicker(
                                    initialDate = LocalDate.of(
                                        screenState.date.year,
                                        screenState.date.monthNumber,
                                        screenState.date.dayOfMonth
                                    ),
                                    colors = DatePickerDefaults.colors(
                                        headerBackgroundColor = MaterialTheme.colorScheme.primary,
                                        calendarHeaderTextColor = MaterialTheme.colorScheme.onBackground,
                                        dateInactiveTextColor = MaterialTheme.colorScheme.onBackground,
                                        dateActiveBackgroundColor = MaterialTheme.colorScheme.secondary
                                    ),
                                    title = "Pick a date"
                                ) {
                                    val day = it.dayOfMonth
                                    val month = it.month.value
                                    val year = it.year
                                    viewModel.setDate(kotlinx.datetime.LocalDate(year, month, day))
                                }
                            }
                            MaterialDialog(
                                dialogState = timeDialogState,
                                backgroundColor = MaterialTheme.colorScheme.background,
                                buttons = {
                                    positiveButton(
                                        text = "Ok",
                                        textStyle = MaterialTheme.typography.bodyMedium
                                            .copy(
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                    )
                                    negativeButton(
                                        text = "Cancel",
                                        textStyle = MaterialTheme.typography.bodyMedium
                                            .copy(
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                    )
                                }
                            ) {
                                timepicker(
                                    initialTime = LocalTime.of(screenState.time.hour, screenState.time.minute),
                                    title = "Pick a time",
                                    is24HourClock = true,
                                    colors = TimePickerDefaults.colors(
                                        activeBackgroundColor = MaterialTheme.colorScheme.primary,
                                        inactiveTextColor = MaterialTheme.colorScheme.onBackground,
                                        headerTextColor = MaterialTheme.colorScheme.onBackground,
                                        selectorColor = MaterialTheme.colorScheme.secondary,
                                        inactiveBackgroundColor = MaterialTheme.colorScheme.onPrimary,
                                        borderColor = Color.Blue,
                                        inactivePeriodBackground = Color.Red
                                    )
                                ) {
                                    val hour = it.hour
                                    val minute = it.minute
                                    viewModel.setTime(kotlinx.datetime.LocalTime(hour, minute))
                                }
                            }
                        }
                    }


                    ActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(56.dp)
                            .align(Alignment.BottomCenter),
                        label = "Save",
                        onClick = {
                            viewModel.saveItem(editId)
                            Toast.makeText(context, "Record has been saved", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    )
                }
            )
        }
    }
}