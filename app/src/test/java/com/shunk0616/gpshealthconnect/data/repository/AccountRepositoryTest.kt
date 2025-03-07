package com.shunk0616.gpshealthconnect.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.shunk0616.gpshealthconnect.data.repository.impl.AccountRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AccountRepositoryTest {

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockAuthTask: Task<com.google.firebase.auth.AuthResult>

    @Mock
    private lateinit var mockUser: FirebaseUser

    private lateinit var repository: AccountRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        repository = AccountRepositoryImpl(mockAuth)
    }

    @Test
    fun isAuthorized_returnsFalse() {
        // 最初は未認証
        assertFalse(repository.isAuthorized)
    }

    @Test
    fun login_callsSignInAnonymously() = runTest {
        // Given
        val successTask = Tasks.forResult(null as com.google.firebase.auth.AuthResult?)
        `when`(mockAuth.signInAnonymously()).thenReturn(successTask)

        // When
        repository.login()

        // Then
        verify(mockAuth).signInAnonymously()
    }
}