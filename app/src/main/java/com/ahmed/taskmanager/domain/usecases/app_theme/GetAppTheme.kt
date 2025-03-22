package com.ahmed.taskmanager.domain.usecases.app_theme

import com.ahmed.taskmanager.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class GetAppTheme(
    private val localUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<String>{
        return localUserManager.readAppTheme()
    }
}