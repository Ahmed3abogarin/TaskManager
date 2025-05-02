package com.ahmed.taskmanager.presentation.navgraph

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmed.taskmanager.domain.model.Priority
import com.ahmed.taskmanager.domain.model.Task
import com.ahmed.taskmanager.presentation.create.CreateScreen
import com.ahmed.taskmanager.presentation.create.CreateViewModel
import com.ahmed.taskmanager.presentation.details.DetailsScreen
import com.ahmed.taskmanager.presentation.details.DetailsViewModel
import com.ahmed.taskmanager.presentation.home.HomeEvent
import com.ahmed.taskmanager.presentation.home.HomeScreen
import com.ahmed.taskmanager.presentation.home.HomeViewModel
import com.ahmed.taskmanager.presentation.settings.SettingsScreen
import com.ahmed.taskmanager.presentation.settings.ThemeViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun TaskNavGraph() {
//    val context = LocalContext.current
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val detailsViewModel: DetailsViewModel = hiltViewModel()
    val createViewModel: CreateViewModel = hiltViewModel()
    val themeViewModel: ThemeViewModel = hiltViewModel()

    val coroutine = rememberCoroutineScope()
    val snackBarState = remember { SnackbarHostState() }

    val showDialog = remember { mutableStateOf(false) }
    var task by remember { mutableStateOf<Task?>(null) }


    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Route.HomeScreen.route) {

            composable(Route.HomeScreen.route) {
                val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                var showBottomDialog by remember { mutableStateOf(false) }

                if (showDialog.value && task != null){
                    DetailsScreen(
                        task!!,
                        event = detailsViewModel::onEvent,
                        navigateUp = { showDialog.value = false},
                        showDialog = showDialog
                    )
                }

                if (showBottomDialog) {
                    CreateScreen(
                        event = {
                            createViewModel.onEvent(it)
                            showBottomDialog = false
                        },
                        sheetState = sheetState,
                        onDismiss = { showBottomDialog = false })
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(snackBarState)
                    },
                    topBar = {
                        TopAppBar(title = { Text(text = "Task Manager") }, actions = {
                            IconButton(onClick = {
                                navController.navigate(Route.SettingsScreen.route)
                            }) {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        })
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                showBottomDialog = true
                            },
                            containerColor = Color.White,
                            contentColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                    }) { innerPadding ->
                    HomeScreen(
                        animatedVisibilityScope = this@composable,
                        state = homeViewModel.state.value,
                        modifier = Modifier.padding(innerPadding),
                        onClick = {
//                            navigateToDetails(navController = navController, task = it)
                            task = it
                            showDialog.value = true

                        },
                        onRemoveClicked = { task ->
                            homeViewModel.onEvent(HomeEvent.DeleteTask(task))
                            coroutine.launch {
                                val result = snackBarState.showSnackbar(
                                    "Task deleted",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Short
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    homeViewModel.onEvent(HomeEvent.UpsertTask(task))
                                }
                            }

                        },
                        event = homeViewModel::onEvent
                    )
                }
            }

            composable(Route.DetailsScreen.route) {

                Box(modifier = Modifier.fillMaxSize()){

                }



            }
            composable(
                Route.SettingsScreen.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 1300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 1300)
                    )
                }
            ) {

                SettingsScreen(
                    viewModel = themeViewModel,
                    navigateUp = { navController.navigate(Route.HomeScreen.route){
                        popUpTo(0)
                    } })
            }
        }
    }


}

private fun navigateToDetails(navController: NavController, task: Task) {
    navController.currentBackStackEntry?.savedStateHandle?.set("task", task)
//    navController.navigate(route = Route.DetailsScreen.route)
}