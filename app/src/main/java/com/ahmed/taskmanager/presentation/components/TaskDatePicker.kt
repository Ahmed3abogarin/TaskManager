package com.ahmed.taskmanager.presentation.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmed.taskmanager.domain.model.AppTheme
import com.ahmed.taskmanager.ui.theme.TaskManagerTheme
import com.ahmed.taskmanager.util.convertDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun taskDatePicker(modifier: Modifier = Modifier,taskDate: String): String{
//    var dateResult by remember { mutableStateOf(taskDate) }
//    val openDialog = remember { mutableStateOf(false) }
//
//    if (openDialog.value) {
//        val datePickerState = rememberDatePickerState()
//        val confirmedEnabled =
//            remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
//
//        DatePickerDialog(
//            onDismissRequest = { openDialog.value = false },
//            confirmButton = {
//                TextButton(
//                    onClick = {
//                        openDialog.value = false
//                        var date = "No Selection"
//                        if (datePickerState.selectedDateMillis != null) {
//                            date =
//                                Tools.convertLongToTime(datePickerState.selectedDateMillis!!)
//                        }
//                        dateResult = date
//                    },
//                    enabled = confirmedEnabled.value
//                ) {
//                    Text(text = "Okay")
//                }
//            }
//        ) {
//            DatePicker(state = datePickerState)
//        }
//    }
//
//    OutlinedButton(
//        onClick = { openDialog.value = true },
//        modifier = modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(8.dp),
//        colors = ButtonDefaults.outlinedButtonColors()
//    ) {
//        Text(
//            text = dateResult,
//            modifier = modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, bottom = 8.dp),
//            fontSize = 16.sp,
//            color = Color.Black
//        )
//    }
//
//    return dateResult
//}

@Composable
fun TaskDateTimePicker(modifier: Modifier = Modifier,onTimeChanged: (LocalTime) -> Unit, onDateChanged: (LocalDate) -> Unit) {
    val context = LocalContext.current
    val now = LocalDateTime.now()

    var selectedDate by remember { mutableStateOf(now.toLocalDate()) }
    var selectedTime by remember { mutableStateOf(now.toLocalTime().withSecond(0).withNano(0)) }


    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }



    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Due Date",
            style = MaterialTheme.typography.headlineSmall
        )
        Row {
            Button(
                onClick = { showDatePicker.value = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(end = 8.dp)

            ) {
                Text(
                    selectedDate.convertDate(),
                    color = Color.Black
                )
            }

            Button(
                onClick = { showTimePicker.value = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(2.dp)
            ) {
                Text(
                    selectedTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                    color = Color.Black
                )
            }

        }


    }

    if (showDatePicker.value) {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                selectedDate = LocalDate.of(year, month + 1, day)
                onDateChanged(selectedDate)
                showDatePicker.value = false
                showTimePicker.value = true
            },
            now.year,
            now.monthValue - 1,
            now.dayOfMonth
        ).show()
    }

    if (showTimePicker.value) {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                selectedTime = LocalTime.of(hour, minute)
                onTimeChanged(selectedTime)
                showTimePicker.value = false
            },
            now.hour,
            now.minute,
            true
        ).show()
    }
}

@Preview
@Composable
fun TimeBack() {
    TaskManagerTheme(selectedTheme = AppTheme.LIGHT_FIRST) {

        TaskDateTimePicker(onTimeChanged = {}, onDateChanged = {})
//        Column(modifier = Modifier.padding(16.dp)) {
//            Button(onClick = { }) {
//                Text("Pick Date: ")
//            }
//
//            Button(onClick = {  }) {
//                Text("Pick Time: ")
//            }
//
//        }
    }
}