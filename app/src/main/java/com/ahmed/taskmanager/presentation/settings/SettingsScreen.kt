package com.ahmed.taskmanager.presentation.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahmed.taskmanager.domain.model.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: ThemeViewModel, showDialog: MutableState<Boolean>) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    val selectedTheme = viewModel.selectedTheme.value

    if (showDialog.value){
        ModalBottomSheet(
            onDismissRequest = {showDialog.value = false},
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.padding(bottom = 12.dp).wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = AppTheme.entries.toList()

                LazyVerticalStaggeredGrid(
                    modifier = Modifier
                        .padding(5.dp),
                    columns = StaggeredGridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                    , verticalItemSpacing = 5.dp
                ) {
                    items(list.size) {
                        val theme = list[it]

                        Button(
                            shape = RoundedCornerShape(16.dp),
                            onClick = {viewModel.updateTheme(theme)}, modifier = Modifier
                                .height(160.dp)
                                .width(120.dp),
                            border = BorderStroke(
                                width = 3.dp,
                                color = if (selectedTheme == theme) Color.Green else Color.LightGray
                            )

                        ) {
                            Text("Theme ${it+1}")
                        }

                    }

                }
            }
        }

    }

}