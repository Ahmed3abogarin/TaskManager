package com.ahmed.taskmanager.presentation.create

import androidx.lifecycle.ViewModel
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.usecases.tasks.AlarmUseCase
import com.ahmed.taskmanager.domain.usecases.tasks.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val alarmUseCase: AlarmUseCase,
) : ViewModel() {


    fun onEvent(event: CreateScreenEvent) {
        when (event) {
            is CreateScreenEvent.UpsertTask -> {
                val task = event.task
                upsertTask(task)
                setAlarm(task)
            }



        }
    }

    private fun setAlarm(task: Task) {
        val taskDateTime = LocalDateTime.of(task.dueDate, task.time)
        val timeInMillis = taskDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        alarmUseCase.setAlarm(timeInMillis, task = task)
    }

    private fun upsertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskUseCases.upsertTask(task)
        }
    }


}