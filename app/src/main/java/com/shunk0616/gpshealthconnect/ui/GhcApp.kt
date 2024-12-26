package com.shunk0616.gpshealthconnect.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shunk0616.gpshealthconnect.data.source.Route
import com.shunk0616.gpshealthconnect.ui.auth.AuthenticationScreen
import com.shunk0616.gpshealthconnect.ui.common.theme.gpshealthconnectTheme
import com.shunk0616.gpshealthconnect.ui.home.HomeScreen
import com.shunk0616.gpshealthconnect.ui.setting.SettingScreen

@Composable
fun GhcApp() {
    val navController = rememberNavController()

    gpshealthconnectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = Route.Home
            ) {
                composable<Route.Home> { HomeScreen() }
                composable<Route.Authenticatioin> { AuthenticationScreen() }
                composable<Route.Setting> { SettingScreen() }
            }
        }
    }


}

