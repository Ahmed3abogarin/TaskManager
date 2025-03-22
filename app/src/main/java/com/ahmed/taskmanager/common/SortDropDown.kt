package com.ahmed.taskmanager.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.ahmed.taskmanager.R

@Composable
fun SortDropdown(onSortClicked: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val sortOptions = listOf("Priority: Low to High", "Priority: High to Low","Completed tasks","Pending tasks")

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(painter = painterResource(R.drawable.ic_filter_list), contentDescription = "Filter icon")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            sortOptions.forEachIndexed { index ,option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                       onSortClicked(index)
                        expanded = false
                    }
                )
            }
        }
    }
}