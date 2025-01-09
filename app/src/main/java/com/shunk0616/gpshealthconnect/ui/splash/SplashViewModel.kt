package com.shunk0616.gpshealthconnect.ui.splash

import com.shunk0616.gpshealthconnect.data.repository.AccountRepository
import com.shunk0616.gpshealthconnect.data.repository.LogRepository
import com.shunk0616.gpshealthconnect.ui.GhcViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    logRepository: LogRepository
) : GhcViewModel(logRepository) {
    fun onAppStart(onAuthorized: () -> Unit, onUnauthorized: () -> Unit) {
        if (accountRepository.isAuthorized) {
            onAuthorized()
        } else {
            onUnauthorized()
        }
    }
}
