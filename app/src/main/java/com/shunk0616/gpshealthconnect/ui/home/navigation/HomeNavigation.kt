package com.shunk0616.gpshealthconnect.ui.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shunk0616.gpshealthconnect.ui.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable object HomeRoute

fun NavController.navigateToHome() {
    navigate(HomeRoute) {
        popUpTo(graph.startDestinationId) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.homeScreen(onSettingClick: () -> Unit) {
    composable<HomeRoute> {
        HomeRoute(onSettingClick)
    }
}
