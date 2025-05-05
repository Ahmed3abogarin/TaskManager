package com.ahmed.taskmanager.domain.usecases.tasks

data class AlarmUseCase(
    val setAlarm: SetAlarm,
    val cancelAlarm: CancelAlarm,
)