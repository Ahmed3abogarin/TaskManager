package com.ahmed.taskmanager.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ahmed.taskmanager.presentation.navgraph.Route
import com.ahmed.taskmanager.presentation.navgraph.TasksNavGraph
import com.ahmed.taskmanager.presentation.onboarding.OnBoardingScreen
import com.ahmed.taskmanager.presentation.onboarding.OnBoardingViewModel

@Composable
fun AppNavGraph(
    startDestination: String,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route
            ) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }

        navigation(
            route = Route.AppMainNavigation.route,
            startDestination = Route.AppNavigatorScreen.route
        ) {
            composable(route = Route.AppNavigatorScreen.route) {
                TasksNavGraph()
            }

        }
    }
}
