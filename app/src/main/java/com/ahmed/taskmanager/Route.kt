package com.ahmed.taskmanager

sealed class Route(val route: String) {
    data object HomeScreen: Route(route = "HomeScreen")
    data object DetailsScreen: Route(route = "DetailsScreen/{task}")
}