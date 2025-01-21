package com.shunk0616.gpshealthconnect.ui.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shunk0616.gpshealthconnect.R

@Composable
internal fun AuthenticationRoute(
    onAuthenticated: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: AuthenticationViewModel = hiltViewModel(
    )
) {
    AuthenticationScreen(
        onSignInClick = { viewModel.onSignInClick(onAuthenticated) },
        onShowSnackbar = onShowSnackbar,
        errorMessage = viewModel.errorMessage,
    )
}

@Composable
fun AuthenticationScreen(
    onSignInClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    errorMessage: String?,
) {
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            onShowSnackbar(errorMessage, null)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = stringResource(id = R.string.auth_screen_title))
        Button(
            onClick = { onSignInClick() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.auth_button_label)
            )
        }
        Text(text = stringResource(id = R.string.auth_screen_description))
    }
}
