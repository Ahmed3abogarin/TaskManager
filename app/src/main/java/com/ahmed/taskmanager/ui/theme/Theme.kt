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
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Red,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val LightFirstColorScheme = lightColorScheme(
    primary = Color.Blue, // circle - radio - border
    tertiary = LightPurple, // Task card
    onSecondary = Color.Red
)

private val DarkFirstColorScheme = darkColorScheme(
    primary = Color.DarkGray
)


private val LightSecondScheme = lightColorScheme(
    primary = Orange,
    tertiary = LightBrown,
//    onBackground = Color.Red, // Text
    onSecondary = Color.Red
)

private val LightThirdScheme = lightColorScheme(
    primary = LightThird2,
    tertiary = LightThird,
//    onBackground = Color.Red, // Text
    onSecondary = Color.Red
)

private val LightFourthScheme = lightColorScheme(
    primary = LightFourth,
    tertiary = LightFourth2,
//    onBackground = Color.Red, // Text
    onSecondary = Color.Red
)

private val DarkSecondScheme = darkColorScheme(
    primary = Color.Green,
    background = Color.Red

)

@Composable
fun TaskManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    selectedTheme: AppTheme,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> when (selectedTheme) {
            AppTheme.LIGHT_FIRST -> LightFirstColorScheme
            AppTheme.DARK_FIRST -> DarkFirstColorScheme
            AppTheme.LIGHT_SECOND -> LightSecondScheme
            AppTheme.DARK_SECOND -> DarkSecondScheme
            AppTheme.LIGHT_THIRD -> LightThirdScheme
            AppTheme.LIGHT_Fourth -> LightFourthScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

