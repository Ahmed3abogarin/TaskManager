package com.ahmed.taskmanager.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {


    suspend fun saveAppTheme()

    fun readAppTheme(): Flow<Boolean>
}