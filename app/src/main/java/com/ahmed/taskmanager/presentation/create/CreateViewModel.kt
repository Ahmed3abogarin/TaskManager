package com.ahmed.taskmanager.presentation.create

import androidx.lifecycle.ViewModel
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.usecases.tasks.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
) : ViewModel() {


    fun onEvent(event: CreateScreenEvent) {
        when (event) {
            is CreateScreenEvent.UpsertTask -> {
                upsertTask(event.task)

            }

        }
    }

    private fun upsertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskUseCases.upsertTask(task)
        }
    }


}