package com.shunk0616.gpshealthconnect.ui.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shunk0616.gpshealthconnect.ui.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable object HomeRoute

fun NavGraphBuilder.homeScreen(onSettingClick: () -> Unit) {
    composable<HomeRoute> {
        HomeRoute(onSettingClick)
    }
}
