package com.ahmed.taskmanager.presentation.navgraph

sealed class Route(val route: String) {
    data object HomeScreen: Route(route = "HomeScreen")
    data object SettingsScreen: Route(route = "SettingsScreen")
    data object DetailsScreen: Route(route = "DetailsScreen")
}