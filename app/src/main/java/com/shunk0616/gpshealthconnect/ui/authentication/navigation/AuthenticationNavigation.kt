package com.shunk0616.gpshealthconnect.ui.authentication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shunk0616.gpshealthconnect.ui.authentication.AuthenticationRoute
import kotlinx.serialization.Serializable

@Serializable object AuthenticationRoute

fun NavController.navigateToAuthentication(navOptions: NavOptions? = null) =
    navigate(AuthenticationRoute, navOptions)

fun NavGraphBuilder.authenticationScreen(
    onAuthenticated: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<AuthenticationRoute> {
        AuthenticationRoute(onAuthenticated, onShowSnackbar)
    }
}
