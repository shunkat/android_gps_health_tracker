package com.shunk0616.gpshealthconnect.ui.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shunk0616.gpshealthconnect.ui.setting.SettingRoute
import kotlinx.serialization.Serializable

@Serializable object SettingRoute

fun NavController.navigateToSetting(navOptions: NavOptions? = null) =
    navigate(SettingRoute, navOptions)

fun NavGraphBuilder.settingScreen(
    onBackClick: () -> Unit,
) {
    composable<SettingRoute> {
        SettingRoute(onBackClick)
    }
}