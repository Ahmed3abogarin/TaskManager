package com.ahmed.taskmanager.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.usecases.TaskUseCases
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

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpsertTask -> {
                upsertTask(event.task)
            }

            is HomeEvent.DeleteTask -> {
                deleteTask(event.task)
            }

            is HomeEvent.GetTasks -> {
                getTasks()
            }

            is HomeEvent.GetSortTasks -> {
                sortTasks(event.sort)
            }
        }
    }

    private fun getTasks() {
        tasksUseCases.getTasks().onEach { tasks ->
            _state.value = _state.value.copy(tasks = tasks)
        }.launchIn(viewModelScope)
    }

    private fun sortTasks(sort: Int){
        when(sort) {
            0 -> getTasksByLowPriority()
            1 -> getTasksByHighPriority()
            else -> getTasks()
        }
    }

    private fun getTasksByLowPriority() {
        tasksUseCases.getByLowPriority().onEach { tasks ->
            _state.value = _state.value.copy(tasks = tasks)
        }.launchIn(viewModelScope)
    }

    private fun getTasksByHighPriority() {
        tasksUseCases.getByHighPriority().onEach { tasks ->
            _state.value = _state.value.copy(tasks = tasks)
        }.launchIn(viewModelScope)
    }


    private fun upsertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksUseCases.upsertTask(task)
        }
    }
    private fun deleteTask(task: Task){
        CoroutineScope(Dispatchers.IO).launch {
            tasksUseCases.deleteTask(task)
        }
    }


}