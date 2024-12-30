package com.shunk0616.gpshealthconnect.ui.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shunk0616.gpshealthconnect.ui.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions) =
    navigate(route = HomeRoute, navOptions)

fun NavGraphBuilder.homeScreen(
    onSettingClick: () -> Unit,
) {
    composable<HomeRoute> {
        HomeRoute(onSettingClick)
    }
}
