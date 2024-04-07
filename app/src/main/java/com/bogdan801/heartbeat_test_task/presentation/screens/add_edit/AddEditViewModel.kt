package com.bogdan801.heartbeat_test_task.presentation.screens.add_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan801.heartbeat_test_task.domain.model.Item
import com.bogdan801.heartbeat_test_task.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel
@Inject
constructor(
    private val repository: Repository
): ViewModel() {
    private val _screenState = MutableStateFlow(AddEditScreenState())
    val screenState = _screenState.asStateFlow()

    fun setSystolicPressure(value: Int){
        _screenState.update {
            it.copy(
                systolicPressure = value
            )
        }
    }

    fun setDiastolicPressure(value: Int){
        _screenState.update {
            it.copy(
                diastolicPressure = value
            )
        }
    }

    fun setPulse(value: Int){
        _screenState.update {
            it.copy(
                pulse = value
            )
        }
    }

    fun setDate(value: LocalDate){
        _screenState.update {
            it.copy(
                date = value
            )
        }
    }

    fun setTime(value: LocalTime){
        _screenState.update {
            it.copy(
                time = value
            )
        }
    }

    fun setUpDefaultsToEdit(editId: Int) {
        viewModelScope.launch {
            val defaults = repository.getItemById(editId)
            if(defaults!=null){
                setSystolicPressure(defaults.systolicPressure)
                setDiastolicPressure(defaults.diastolicPressure)
                setPulse(defaults.pulse)

                val time = LocalTime(defaults.datetime.hour, defaults.datetime.minute, defaults.datetime.second)
                setTime(time)

                val date = LocalDate(defaults.datetime.year, defaults.datetime.monthNumber, defaults.datetime.dayOfMonth)
                setDate(date)
            }
        }
    }

    fun saveItem(editId: Int?) {
        viewModelScope.launch {
            if(editId == null){
                repository.insertItem(
                    Item(
                        systolicPressure = _screenState.value.systolicPressure,
                        diastolicPressure = _screenState.value.diastolicPressure,
                        pulse = _screenState.value.pulse,
                        datetime = LocalDateTime(
                            date = _screenState.value.date,
                            time = _screenState.value.time
                        )
                    )
                )
            }
            else {
                repository.updateItem(
                    Item(
                        itemID = editId,
                        systolicPressure = _screenState.value.systolicPressure,
                        diastolicPressure = _screenState.value.diastolicPressure,
                        pulse = _screenState.value.pulse,
                        datetime = LocalDateTime(
                            date = _screenState.value.date,
                            time = _screenState.value.time
                        )
                    )
                )
            }
        }
    }
}