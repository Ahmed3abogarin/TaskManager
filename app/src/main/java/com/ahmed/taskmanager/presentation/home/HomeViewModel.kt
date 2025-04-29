package com.ahmed.taskmanager.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.usecases.tasks.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tasksUseCases: TaskUseCases,
) : ViewModel() {


    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        getTasks()
    }


//    private val _filter = MutableStateFlow(_state.value.filter)
//    private val _sortOrder = MutableStateFlow(_state.value.sortOrder)
//
//
//
//
//    val tasks = combine(_filter, _sortOrder) { filter, sortOrder ->
//        Pair(filter, sortOrder)
//    }.flatMapLatest { (filter, sortOrder) ->
//        tasksUseCases.getTasksList(filter, sortOrder)
//    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    fun onEvent(event: HomeEvent) {

        CoroutineScope(Dispatchers.IO).launch {
            when (event) {
                is HomeEvent.UpsertTask -> {
                    upsertTask(event.task)
                    getTasks()
                }

                is HomeEvent.DeleteTask -> deleteTask(event.task)

                is HomeEvent.UpdateTaskSort -> {
                    _state.value = _state.value.copy(sortOrder = event.sortOrder)
                    getTasks()

                }

                is HomeEvent.UpdateTaskFilter -> {
                    _state.value = _state.value.copy(filter = event.filter)
                    getTasks()
                }
            }

        }
    }

    private fun getTasks() {
        Log.v("TTTOOL", "Filter: ${_state.value.filter} Sort: ${_state.value.sortOrder}")
        tasksUseCases.getTasksList(_state.value.filter, _state.value.sortOrder).onEach { tasks ->
            _state.value = _state.value.copy(tasks = tasks)
        }.launchIn(viewModelScope)
        _state.value = _state.value.copy(isLoading = false)



    }


    private suspend fun upsertTask(task: Task) {

        tasksUseCases.upsertTask(task)

    }

    private suspend fun deleteTask(task: Task) {
        tasksUseCases.deleteTask(task)

    }


}