package com.bogdan801.heartbeat_test_task.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.bogdan801.heartbeat_test_task.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val repository: Repository
): ViewModel() {
    private val _screenState = MutableStateFlow(HomeScreenState())
    val screenState = _screenState.asStateFlow()

    fun setValue(value: String){
        _screenState.update {
            it.copy(
                state = value
            )
        }
    }

}