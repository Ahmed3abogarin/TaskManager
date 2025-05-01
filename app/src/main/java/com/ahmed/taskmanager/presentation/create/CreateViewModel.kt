package com.ahmed.taskmanager.presentation.create

import androidx.lifecycle.ViewModel
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.domain.usecases.AlarmUseCase
import com.ahmed.taskmanager.domain.usecases.tasks.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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
                setAlarm(task.id, task, task.dueDate,task.time)
            }



        }
    }

    private fun setAlarm(taskId: Int, task: Task, taskDate: LocalDate, taskTime: LocalTime) {
        val taskDateTime = LocalDateTime.of(taskDate, taskTime)
        val timeInMillis = taskDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        alarmUseCase.setAlarm(taskId = taskId, timeInMillis, task = task)
    }

    private fun upsertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskUseCases.upsertTask(task)
        }
    }


}