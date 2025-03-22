package com.ahmed.taskmanager.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.ahmed.taskmanager.domain.model.AppTheme

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = PurpleGrey80,
    outline = Color.LightGray // for the back line
)

private val LightFirstColorScheme = lightColorScheme(
    primary = Color.Blue, // circle - radio - border
    tertiary = LightPurple, // Task card
    onSecondary = Color.Red,
    outline = Color.Black
)


private val LightSecondScheme = lightColorScheme(
    primary = Orange,
    tertiary = LightBrown,
    onSecondary = Color.Red,
    outline = Color.Black
)

private val LightThirdScheme = lightColorScheme(
    primary = LightThird2,
    tertiary = LightThird,
    onSecondary = Color.Red,
    outline = Color.Black
)

private val LightFourthScheme = lightColorScheme(
    primary = LightFourth,
    tertiary = LightFourth2,
    onSecondary = Color.Red,
    outline = Color.Black
)

private val LightFifthScheme = lightColorScheme(
    primary =LightPink, // circle - radio - border
    tertiary = LightPink2, // Task card
    onSecondary = LightPink,
    outline = Color.Black
)


private val LightSixthScheme = lightColorScheme(
    primary = LightGreen2,
    tertiary = LightGreen,
    onSecondary = Color.DarkGray,
    outline = Color.Black
)

@Composable
fun TaskManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    selectedTheme: AppTheme,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> when (selectedTheme) {
            AppTheme.LIGHT_FIRST -> LightFirstColorScheme
            AppTheme.LIGHT_SECOND -> LightSecondScheme
            AppTheme.LIGHT_THIRD -> LightThirdScheme
            AppTheme.LIGHT_FOURTH -> LightFourthScheme
            AppTheme.LIGHT_FIFTH -> LightFifthScheme
            AppTheme.LIGHT_SIXTH -> LightSixthScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

