package com.ahmed.taskmanager.domain.usecases.app_entry

import com.ahmed.taskmanager.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class GetAppEntry(
    private val localUserManager: LocalUserManager,
) {
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}