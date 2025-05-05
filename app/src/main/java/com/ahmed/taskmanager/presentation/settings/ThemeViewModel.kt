package com.ahmed.taskmanager.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.taskmanager.domain.model.AppTheme
import com.ahmed.taskmanager.domain.usecases.app_theme.ThemeUseCases
import com.ahmed.taskmanager.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeUseCases: ThemeUseCases
):ViewModel() {

    private val _selectedTheme = mutableStateOf(AppTheme.LIGHT_FIRST)
    val selectedTheme = _selectedTheme

    var startDestination by mutableStateOf<String?>(null)
        private set

    init {
        getAppEntry()
        getTheme()
    }

    private fun getAppEntry(){
        themeUseCases.getAppEntry().onEach {
            startDestination = if (it) Route.AppMainNavigation.route else Route.AppStartNavigation.route
        }.launchIn(viewModelScope)

    }

    private fun getTheme(){
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