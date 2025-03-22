package com.ahmed.taskmanager.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var sideEffect by mutableStateOf<String?>(null)
        private set


    init {
        getTasks()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpsertTask -> upsertTask(event.task)
            is HomeEvent.DeleteTask -> deleteTask(event.task)
            is HomeEvent.GetTasks ->getTasks()
            is HomeEvent.GetSortTasks -> sortTasks(event.sort)
            is HomeEvent.RemoveSideEffect -> sideEffect = null
        }
    }

    private fun getTasks() {
        _state.value = _state.value.copy(isLoading = true)
        tasksUseCases.getTasks().onEach { tasks ->
            _state.value = _state.value.copy(taskCount = tasks.size)
            val completed = tasks.filter { it.done }
            _state.value = _state.value.copy(completedTasks = completed.size )
            _state.value = _state.value.copy(tasks = tasks)
        }.launchIn(viewModelScope)
        _state.value = _state.value.copy(isLoading = false)
    }

    private fun sortTasks(sort: Int) {
        when (sort) {
            0 -> getTasksByLowPriority()
            1 -> getTasksByHighPriority()
            2 -> getCompletedTasks()
            3 -> getUncompletedTasks()
            else -> getTasks()
        }
    }

    private fun getUncompletedTasks() {
        tasksUseCases.getTasks().onEach { tasks ->
            val uncompleted = tasks.filter { !it.done }
            _state.value = _state.value.copy(tasks = uncompleted)
        }.launchIn(viewModelScope)
    }

    private fun getCompletedTasks() {
        tasksUseCases.getTasks().onEach { tasks ->
            val uncompleted = tasks.filter { it.done }
            _state.value = _state.value.copy(tasks = uncompleted)
        }.launchIn(viewModelScope)
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

    private fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksUseCases.deleteTask(task)
            sideEffect = "Task deleted"
        }
    }


}