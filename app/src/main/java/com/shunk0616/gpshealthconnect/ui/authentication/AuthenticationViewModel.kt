package com.shunk0616.gpshealthconnect.ui.authentication

import androidx.compose.runtime.mutableStateOf
import com.shunk0616.gpshealthconnect.data.repository.AccountRepository
import com.shunk0616.gpshealthconnect.data.repository.LogRepository
import com.shunk0616.gpshealthconnect.ui.GhcViewModel
import com.shunk0616.gpshealthconnect.ui.common.ext.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class AuthenticationUiState(
    val errorMessage: String? = null
)

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    logRepository: LogRepository
) : GhcViewModel(logRepository) {
    var uiState = mutableStateOf(AuthenticationUiState())
        private set

    val errorMessage
        get() = uiState.value.errorMessage

    fun onSignInClick(onAuthenticated: () -> Unit) {
        launchCatching {
            accountRepository.login()
            onAuthenticated()
        }
    }
}
