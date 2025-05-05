package com.ahmed.taskmanager.presentation.navgraph

sealed class Route(val route: String) {
    data object HomeScreen: Route(route = "HomeScreen")
    data object SettingsScreen: Route(route = "SettingsScreen")

    data object OnBoardingScreen: Route(route = "onBoardingScreen")

    // Navigation
    data object AppStartNavigation: Route(route = "appStartScreen")
    data object AppMainNavigation: Route(route = "appMainScreen")
    data object AppNavigatorScreen: Route("appNavigationScreen")
}