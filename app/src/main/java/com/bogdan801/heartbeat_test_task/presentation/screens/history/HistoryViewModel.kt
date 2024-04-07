package com.bogdan801.heartbeat_test_task.presentation.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan801.heartbeat_test_task.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel
@Inject
constructor(
    private val repository: Repository
): ViewModel() {
    private val _screenState = MutableStateFlow(HistoryScreenState())
    val screenState = _screenState.asStateFlow()

    fun setValue(value: String){
        _screenState.update {
            it.copy(
                state = value
            )
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.deleteAllItems()
        }
    }

}