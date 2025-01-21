package com.shunk0616.gpshealthconnect.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shunk0616.gpshealthconnect.data.repository.LogRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class GhcViewModel(
    private val logRepository: LogRepository
) : ViewModel() {
    fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                logRepository.logNonFatalCrash(throwable)
            },
            block = block
        )
}
