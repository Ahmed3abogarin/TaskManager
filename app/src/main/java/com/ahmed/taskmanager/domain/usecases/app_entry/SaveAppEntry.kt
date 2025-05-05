package com.ahmed.taskmanager.domain.usecases.app_entry

import com.ahmed.taskmanager.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager,
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}
