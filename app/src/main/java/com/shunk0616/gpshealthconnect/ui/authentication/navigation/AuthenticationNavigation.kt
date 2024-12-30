package com.shunk0616.gpshealthconnect.ui.authentication.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shunk0616.gpshealthconnect.ui.authentication.AuthenticationRoute
import kotlinx.serialization.Serializable

@Serializable object AuthenticationRoute

fun NavGraphBuilder.authenticationScreen(onFormCompleted: () -> Unit) {
    composable<AuthenticationRoute> {
        AuthenticationRoute(onFormCompleted)
    }
}
