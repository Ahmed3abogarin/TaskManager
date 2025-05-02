package com.ahmed.taskmanager.domain.usecases

import com.ahmed.taskmanager.domain.usecases.tasks.CancelAlarm
import com.ahmed.taskmanager.domain.usecases.tasks.SetAlarm

data class AlarmUseCase(
    val setAlarm: SetAlarm,
    val cancelAlarm: CancelAlarm,
)