package com.ahmed.taskmanager.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.taskmanager.domain.usecases.app_theme.ThemeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val settingUseCases: ThemeUseCases
): ViewModel() {

    fun onEvent(event: OnBoardingEvent){
        when(event){
            is OnBoardingEvent.SaveAppEntry -> {
                saveAppEntry()

            }
        }
    }
    private fun saveAppEntry(){
        viewModelScope.launch {
            settingUseCases.saveAppEntry()
        }
    }
}