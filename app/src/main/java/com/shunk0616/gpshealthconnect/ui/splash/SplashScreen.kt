package com.shunk0616.gpshealthconnect.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay

private const val SPLASH_TIMEOUT = 10_000L // 10秒

@Composable
internal fun SplashRoute(
    onAuthorized: () -> Unit,
    onUnauthorized: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    SplashScreen(
        onAppStart = { viewModel.onAppStart(onAuthorized, onUnauthorized) },
        splashTimeout = SPLASH_TIMEOUT
    )
}

@Composable
fun SplashScreen(onAppStart: () -> Unit, splashTimeout: Long) {
    // ローディング表示
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

    // splashTimeout(10秒)待ってから onAppStart を呼び出す
    LaunchedEffect(Unit) {
        delay(splashTimeout)
        onAppStart()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen(
        onAppStart = {},
        splashTimeout = SPLASH_TIMEOUT
    )
}
