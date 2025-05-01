package com.ahmed.taskmanager.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahmed.taskmanager.ui.theme.AlarmBackgroundColor
import kotlinx.coroutines.delay


@Composable
fun PulsingCircle() {
    val infiniteTransition = rememberInfiniteTransition()

    val sizes = listOf(130.dp, 180.dp, 230.dp)

    val scaleFactors = List(sizes.size) { index ->
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.4f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 6000
                    0.0f at 0 using LinearEasing
                    0.2f at (1000 + (index * 1000)) using LinearEasing
                    1.2f at (2000 + (index * 1000)) using LinearEasing
                    1.4f at (4000 + (index * 1000)) using LinearEasing
                    1.6f at 6000 using LinearEasing

                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    val alphaValues = List(sizes.size) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 6000
                    0.0f at 0 using LinearEasing
                    0.2f at (2000 + (index + 1000)) using LinearEasing
                    0.1f at (4000 + (index + 1000)) using LinearEasing
                    0.0f at 6000 using LinearEasing

                },
                repeatMode = RepeatMode.Restart
            )
        )
    }




    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        sizes.forEachIndexed { index, size ->
            val scale = scaleFactors[index].value
            val alpha = alphaValues[index].value
            val radius = size.toPx() / 2


            drawCircle(
                color = AlarmBackgroundColor,
                radius = radius * scale,
                center = center,
                alpha = alpha
            )
        }
    }


}


@Composable
fun animatedGradientBackground(): Brush {
    var color1 by remember { mutableStateOf(Color(0xFFA8E6CF)) }
    var color2 by remember { mutableStateOf(Color(0xFFDCEDC1)) }

    val animatedColor1 by animateColorAsState(
        targetValue = color1,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animatedColor2 by animateColorAsState(
        targetValue = color2,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        while (true) {
            // Cycle through different color pairs
            color1 = Color(listOf(0xFFA8E6CF, 0xFFFFAAAA, 0xFFBBDDDD).random())
            color2 = Color(listOf(0xFFDCEDC1, 0xFFFFCCAA, 0xFFCCEEFF).random())
            delay(3000)
        }
    }

    return Brush.linearGradient(
        colors = listOf(animatedColor1, animatedColor2),
        start = Offset.Zero,
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )
}

