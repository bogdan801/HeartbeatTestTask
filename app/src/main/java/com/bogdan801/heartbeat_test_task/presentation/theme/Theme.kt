package com.bogdan801.heartbeat_test_task.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = green600,
    secondary = green700,
    tertiary = green400,
    onPrimary = Color.Black,
    background = gray800,
    surface = gray900,
    onBackground = Color.White,
    onSurface = gray500,
    outline = gray250,
    error = red600
)

private val LightColorScheme = lightColorScheme(
    primary = green500,
    secondary = green700,
    tertiary = green400,
    onPrimary = Color.White,
    background = gray100,
    surface = gray010,
    onBackground = Color.Black,
    onSurface = gray500,
    outline = gray250,
    error = red500
)

@Composable
fun HeartbeatTestTaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.tertiary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}