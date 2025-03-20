package com.ahmed.taskmanager.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.taskmanager.create.CreateScreenEvent
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

    fun onEvent(event: CreateScreenEvent) {
        when (event) {
            is CreateScreenEvent.UpsertTask -> {
                upsertTask(event.task)
            }
        }
    }

    private fun getTasks() {
            _state.value = _state.value.copy(isLoading = true)
            tasksUseCases.getTasks().onEach { tasks ->
                _state.value = _state.value.copy(tasks = tasks)
            }.launchIn(viewModelScope)
            _state.value = _state.value.copy(isLoading = false)


    }

    private fun upsertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksUseCases.upsertTask(task)
        }
    }

}