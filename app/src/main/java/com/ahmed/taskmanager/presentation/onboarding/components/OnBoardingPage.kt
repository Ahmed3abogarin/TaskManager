package com.ahmed.taskmanager.presentation.onboarding.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ahmed.taskmanager.presentation.onboarding.Page
import com.ahmed.taskmanager.ui.theme.DarkGreen
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun OnBoarding(page: Page, selectedIndex: Int, pagesCount: Int) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(page.animation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )



    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.size(400.dp),
            progress = { progress } ,
            contentScale = ContentScale.Fit,
            composition = composition
        )

        PageIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(64.dp),
            pagesCount = pagesCount,
            selectedPage = selectedIndex
        )

        Spacer(modifier = Modifier.height(24.dp))


        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            color = DarkGreen,
            text = page.text,
            minLines = 4,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(36.dp))


    }


}

