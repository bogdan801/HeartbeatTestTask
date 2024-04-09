package com.bogdan801.heartbeat_test_task.presentation.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan801.heartbeat_test_task.domain.model.Item
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

    private fun updateList(value: List<Item>){
        _screenState.update {
            it.copy(
                displayItems = value
            )
        }
    }

    fun deleteItem(item: Item) {
        _screenState.update {
            it.copy(
                deletedItem = item
            )
        }
        viewModelScope.launch {
            repository.deleteItem(item.itemID)
        }
    }

    fun restoreItem(){
        if(_screenState.value.deletedItem != null){
            val oldId = _screenState.value.deletedItem!!.itemID
            viewModelScope.launch {
                val id = repository.insertItem(_screenState.value.deletedItem!!).toInt()
                repository.updateID(id, oldId)
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.deleteAllItems()
        }
    }

    init {
        viewModelScope.launch {
            repository.getItems().collect{
                updateList(it)
            }
        }
    }
}