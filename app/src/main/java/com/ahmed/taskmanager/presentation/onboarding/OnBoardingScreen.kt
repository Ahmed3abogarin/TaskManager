package com.ahmed.taskmanager.presentation.onboarding

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ahmed.taskmanager.presentation.onboarding.components.OnBoarding
import com.ahmed.taskmanager.ui.theme.DarkGreen
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun OnBoardingScreen(event: (OnBoardingEvent) -> Unit) {
    val coroutine = rememberCoroutineScope()

    val density = LocalDensity.current
    var paddleOffsetX by remember { mutableFloatStateOf(0f) }

    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column {
            HorizontalPager(pagerState) { index ->
                OnBoarding(
                    page = pages[index],
                    selectedIndex = pagerState.currentPage,
                    pagesCount = pages.size
                )
            }

            if (pagerState.currentPage == pages.size - 1) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF96AD9D))
                        .clipToBounds(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    val parentWidthPx = with(density) { maxWidth.toPx() }
                    val boxWidthPx = with(density) { 100.dp.toPx() }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(10) {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                tint = Color.White.copy(alpha = 0.6f),
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .offset { IntOffset(paddleOffsetX.roundToInt(), 0) }
                            .clip(RoundedCornerShape(14.dp))
                            .width(100.dp)
                            .height(50.dp)
                            .background(DarkGreen)
                            .draggable(
                                orientation = Orientation.Horizontal,
                                state = rememberDraggableState { delta ->
                                    val maxOffset = parentWidthPx - boxWidthPx
                                    paddleOffsetX = (paddleOffsetX + delta).coerceIn(0f, maxOffset)
                                },
                                onDragStopped = {
                                    if (paddleOffsetX >= parentWidthPx - boxWidthPx) {
                                        coroutine.launch {
                                            event(OnBoardingEvent.SaveAppEntry)
                                        }
                                    } else {
                                        coroutine.launch {
                                            animate(
                                                initialValue = paddleOffsetX,
                                                targetValue = 0f,
                                                animationSpec = tween(durationMillis = 300)
                                            ) { value, _ ->
                                                paddleOffsetX = value
                                            }
                                        }
                                    }
                                }),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Start", color = Color.White)
                    }
                }
            } else {

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    onClick = {
                        coroutine.launch {
                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
                ) {
                    Text(text = "Next", modifier = Modifier.padding(6.dp))
                }


            }


        }
    }


}