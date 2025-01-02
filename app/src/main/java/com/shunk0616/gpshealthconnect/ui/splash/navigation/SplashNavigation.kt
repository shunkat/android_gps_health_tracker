package com.shunk0616.gpshealthconnect.ui.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shunk0616.gpshealthconnect.ui.splash.SplashRoute
import kotlinx.serialization.Serializable

@Serializable object SplashRoute

fun NavGraphBuilder.splashScreen() {
    composable<SplashRoute> {
        SplashRoute()
    }
}