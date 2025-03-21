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
        filterTasks()
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

            is HomeEvent.FilterTasks -> {
                filterTasks(event.filter)
            }
        }
    }

    private fun getTasks() {
        tasksUseCases.getTasks().onEach { tasks ->
            _state.value = _state.value.copy(tasksCount = tasks.size)
            val ss = tasks.filter { it.done }
            _state.value = _state.value.copy(completedTasks = ss.size )
            _state.value = _state.value.copy(tasks = tasks)
        }.launchIn(viewModelScope)
    }

    // Filter functions
    private fun filterTasks(filter: Int = 0) {
        when (filter) {
            0 -> getTasks()
            1 -> getCompleteTasks()
            2 -> getUnCompleteTasks()
        }
    }

    private fun getCompleteTasks() {
        tasksUseCases.getTasks().onEach { tasks ->
            val filteredTasks = tasks.filter { it.done }
            _state.value = _state.value.copy(tasks = filteredTasks)
        }.launchIn(viewModelScope)
    }

    private fun getUnCompleteTasks() {
        tasksUseCases.getTasks().onEach { tasks ->
            val filteredTasks = tasks.filter { !it.done }
            _state.value = _state.value.copy(tasks = filteredTasks)
        }.launchIn(viewModelScope)

    }


    // sort functions
    private fun sortTasks(sort: Int) {
        when (sort) {
            0 -> getTasksByLowPriority()
            1 -> getTasksByHighPriority()
            else -> getTasks()
        }
    }

    private fun getTasksByLowPriority() {
        val tasks = state.value.tasks
        val sortedTasks = tasks.sortedByDescending { task ->
            when {
                task.priority.name.startsWith("H") -> 1
                task.priority.name.startsWith("M") -> 2
                task.priority.name.startsWith("L") -> 3
                else -> 4
            }
        }
        _state.value = _state.value.copy(tasks = sortedTasks)
    }

    private fun getTasksByHighPriority() {
        val tasks = state.value.tasks
        val sortedTasks = tasks.sortedBy { task ->
            when {
                task.priority.name.startsWith("H") -> 1
                task.priority.name.startsWith("M") -> 2
                task.priority.name.startsWith("L") -> 3
                else -> 4
            }
        }
        _state.value = _state.value.copy(tasks = sortedTasks)
    }


    private fun upsertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksUseCases.upsertTask(task)
        }
    }

    private fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksUseCases.deleteTask(task)
        }
    }


}