package com.shunk0616.gpshealthconnect.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.shunk0616.gpshealthconnect.domain.service.gps.GPSLocationManager
import com.shunk0616.gpshealthconnect.ui.GhcAppState
import com.shunk0616.gpshealthconnect.ui.authentication.navigation.authenticationScreen
import com.shunk0616.gpshealthconnect.ui.authentication.navigation.navigateToAuthentication
import com.shunk0616.gpshealthconnect.ui.home.navigation.homeScreen
import com.shunk0616.gpshealthconnect.ui.home.navigation.navigateToHome
import com.shunk0616.gpshealthconnect.ui.setting.navigation.navigateToSetting
import com.shunk0616.gpshealthconnect.ui.setting.navigation.settingScreen
import com.shunk0616.gpshealthconnect.ui.splash.navigation.SplashRoute
import com.shunk0616.gpshealthconnect.ui.splash.navigation.splashScreen

@Composable
fun GhcNavHost(
    appState: GhcAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    gpsLocationManager: GPSLocationManager
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = SplashRoute,
        modifier = modifier
    ) {
        homeScreen(
            gpsLocationManager = gpsLocationManager
        )
        settingScreen(
            onBackClick = { navController.popBackStack() }
        )

        authenticationScreen(
            onAuthenticated = { navController.navigateToHome() },
            onShowSnackbar = onShowSnackbar
        )

        splashScreen(
            onAuthorized = { navController.navigateToHome() },
            onUnauthorized = { navController.navigateToAuthentication() }
        )
    }
}
