package com.ahmed.taskmanager.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ahmed.taskmanager.util.Dimens.PRIORITY_INDICATOR_SIZE
import com.ahmed.taskmanager.domain.model.Task
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TaskCard(modifier: Modifier, task: Task, onClick: () -> Unit,onRadioClicked: (Task) -> Unit,onRemove: () -> Unit,animatedVisibilityScope: AnimatedVisibilityScope) {
    val coroutineScope = rememberCoroutineScope()
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if (state == SwipeToDismissBoxValue.EndToStart){
                coroutineScope.launch {
                    onRemove()
                }
                true
            }else{
                false
            }
        }
    )

    // Ensure the radio button updates with the latest primary color
    val selectedColor by rememberUpdatedState(MaterialTheme.colorScheme.primary)

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        backgroundContent = {
            Box(modifier = Modifier.fillMaxSize().background(Color.Transparent))
        },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val parts = task.time.split(" ")
            Text(
                text = "${parts[0]}\n${parts[1]}",
                textAlign = TextAlign.Center
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = task.done,onClick = {
                    onRadioClicked(task)
                }, modifier = Modifier.size(16.dp), colors = RadioButtonDefaults.colors(
                    disabledSelectedColor = Color.Black,
                    disabledUnselectedColor = Color.Black,
                    selectedColor = selectedColor, unselectedColor = Color.Black
                ))

                // the line from the circle to the end of the screen
                HorizontalDivider(modifier = Modifier.width(6.dp), color = Color.Black)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(MaterialTheme.colorScheme.tertiary)
                            .weight(0.9f)
                            .clickable(onClick = onClick)
                            .sharedElement(
                                state = rememberSharedContentState(key = "task ${task.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            ),
                        verticalArrangement = Arrangement.spacedBy(8.dp)

                    ) {


                        Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp, start = 12.dp, end = 12.dp), horizontalArrangement = Arrangement.SpaceBetween){
                            Text(
                                text = task.title,
                            )

                            Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)){
                                drawCircle(color = task.priority.color)
                            }
                        }


                        Text(
                            text = task.description,
                            modifier = Modifier.padding(start = 12.dp),
                            color = Color.Gray
                        )

                        Text(
                            text = task.dueDate,
                            modifier = Modifier.padding(start = 12.dp, bottom = 12.dp),
                            color = Color.Gray
                        )


                    }
                    HorizontalDivider(modifier = Modifier.width(6.dp).weight(0.1f), color = Color.Black)


                }

            }

        }

    }


}

//@Preview
//@Composable
//fun CardPreview() {
//    TaskManagerTheme {
//        TaskCard()
//    }
//}