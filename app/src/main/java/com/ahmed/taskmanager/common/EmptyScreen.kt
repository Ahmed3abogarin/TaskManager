package com.ahmed.taskmanager.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ahmed.taskmanager.R

@Composable
fun EmptyScreen(){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.ic_empty), contentDescription = "empty state illustration")
        Text(text = "No tasks to show", color = Color.LightGray)
    }
}


@Preview
@Composable
fun EmptyPreview(){

}