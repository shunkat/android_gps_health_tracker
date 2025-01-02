package com.shunk0616.gpshealthconnect.ui.splash

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

private const val SPLASH_TIMEOUT = 1000L

@Composable
internal fun SplashRoute(
) {
    SplashScreen()
}

@Composable
fun SplashScreen() {
        CircularProgressIndicator()
}
