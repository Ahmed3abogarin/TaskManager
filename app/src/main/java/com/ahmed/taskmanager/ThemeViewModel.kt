package com.ahmed.taskmanager

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.taskmanager.domain.usecases.app_theme.ThemeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeUseCases: ThemeUseCases
):ViewModel() {

    private val _selectedTheme = mutableStateOf(AppTheme.LIGHT_FIRST)
    val selectedTheme = _selectedTheme

    init {
        getTheme()
    }

    fun getTheme(){
        Log.v("THEMEVIEWMODEL",_selectedTheme.value.name)
        viewModelScope.launch {
            themeUseCases.readAppTheme().collect{
                _selectedTheme.value = AppTheme.valueOf(it)
            }
        }
    }

    fun updateTheme(appTheme: AppTheme){
        viewModelScope.launch {
            themeUseCases.saveAppTheme(appTheme.name)
        }

    }
}