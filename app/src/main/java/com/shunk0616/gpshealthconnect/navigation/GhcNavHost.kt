package com.shunk0616.gpshealthconnect.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import com.shunk0616.gpshealthconnect.ui.GhcAppState
import com.shunk0616.gpshealthconnect.ui.home.navigation.HomeRoute
import com.shunk0616.gpshealthconnect.ui.home.navigation.homeScreen
import com.shunk0616.gpshealthconnect.ui.setting.navigation.navigateToSetting
import com.shunk0616.gpshealthconnect.ui.setting.navigation.settingScreen

@Composable
fun GhcNavHost(
    appState: GhcAppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        homeScreen(
            onSettingClick = { navController.navigateToSetting() }
        )
        settingScreen(
            onBackClick = { navController.popBackStack() }
        )
    }
}