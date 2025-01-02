package com.shunk0616.gpshealthconnect.ui.splash

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay

private const val SPLASH_TIMEOUT = 1000L

@Composable
internal fun SplashRoute(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    SplashScreen(
        onAppStart = {
            viewModel.onAppStart(openAndPopUp)
        },
        shouldShowError = viewModel.showError.value,
    )
}

@Composable
fun SplashScreen(
    onAppStart: () -> Unit,
    shouldShowError: Boolean,
) {
    if(shouldShowError) {
        Button(onAppStart) {
            Text("もう一度")
        }
    } else {
        CircularProgressIndicator()
    }


    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        onAppStart()
    }
}