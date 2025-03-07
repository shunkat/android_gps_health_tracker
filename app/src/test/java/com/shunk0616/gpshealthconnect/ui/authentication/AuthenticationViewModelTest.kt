package com.shunk0616.gpshealthconnect.ui.authentication

import com.shunk0616.gpshealthconnect.data.repository.AccountRepository
import com.shunk0616.gpshealthconnect.data.repository.LogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AuthenticationViewModelTest {

    @Mock
    private lateinit var accountRepository: AccountRepository

    @Mock
    private lateinit var logRepository: LogRepository

    private lateinit var viewModel: AuthenticationViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = AuthenticationViewModel(accountRepository, logRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onSignInClick_callsLoginAndCallback() = runTest {
        // Given
        var callbackInvoked = false
        val callback = { callbackInvoked = true }

        // When
        viewModel.onSignInClick(callback)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(accountRepository).login()
        assert(callbackInvoked)
    }

    @Test
    fun initialState_hasNoErrorMessage() {
        // 初期化時にはエラーメッセージは出ない
        assert(viewModel.errorMessage == null)
        verifyNoInteractions(accountRepository)
        verifyNoInteractions(logRepository)
    }
}