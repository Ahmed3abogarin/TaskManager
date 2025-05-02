package com.ahmed.taskmanager.domain.usecases.tasks

import com.ahmed.taskmanager.domain.repository.AlarmScheduler

class CancelAlarm(
    private val alarmScheduler: AlarmScheduler
) {
    operator fun invoke(taskId: Int){
        alarmScheduler.cancelTaskAlarm(taskId)
    }
}