package com.ahmed.taskmanager.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
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
import com.ahmed.taskmanager.Route
import com.ahmed.taskmanager.create.CreateScreen
import com.ahmed.taskmanager.create.CreateViewModel
import com.ahmed.taskmanager.details.DetailsScreen
import com.ahmed.taskmanager.details.DetailsViewModel
import com.ahmed.taskmanager.domain.model.Task

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun TaskNavGraph() {
//    val context = LocalContext.current
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val detailsViewModel: DetailsViewModel = hiltViewModel()
    val createViewModel: CreateViewModel = hiltViewModel()

    val coroutine = rememberCoroutineScope()
    val snackBarState = remember { SnackbarHostState() }



    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Route.HomeScreen.route) {

            composable(Route.HomeScreen.route) {
                val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                var showBottomDialog by remember { mutableStateOf(false) }

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

                    },
                    topBar = { TopAppBar(title = { Text(text = "Task Manager") }) },
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
                        viewModel = homeViewModel,
                        modifier = Modifier.padding(innerPadding),
                        onClick = {
                            navigateToDetails(navController = navController, task = it)
                        },
                        event = homeViewModel::onEvent
                    )
                }
            }

            composable(Route.DetailsScreen.route) {
                navController.previousBackStackEntry?.savedStateHandle?.get<Task>("task")?.let {
                    DetailsScreen(
                        it,
                        animatedVisibilityScope = this@composable,
                        event = detailsViewModel::onEvent,
                        navigateUp = { navController.navigateUp() }
                    )
                }

            }
        }
    }


}

private fun navigateToDetails(navController: NavController, task: Task) {
    navController.currentBackStackEntry?.savedStateHandle?.set("task", task)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}