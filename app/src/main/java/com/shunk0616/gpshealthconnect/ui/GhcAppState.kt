package com.shunk0616.gpshealthconnect.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberGhcAppState(navController: NavHostController = rememberNavController()): GhcAppState {
    return remember(navController) {
        GhcAppState(
            navController = navController
        )
    }
}

class GhcAppState(
    val navController: NavHostController
) {
    // 前回の画面を保存
    private val previousDesitnation = mutableStateOf<NavDestination?>(null)

    // 現在の画面を取得、できなければ前回の画面を返す
    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow.collectAsState(
                initial = null
            )
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDesitnation.value = destination
                }
            } ?: previousDesitnation.value
        }

    val isAuthenticated: Boolean
        @Composable get() {
            // TODO: firebaseの認証情報を取得するdata層のrepository
            return true
        }
}
