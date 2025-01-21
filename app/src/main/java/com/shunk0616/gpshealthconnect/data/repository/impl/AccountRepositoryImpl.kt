package com.shunk0616.gpshealthconnect.data.repository.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.shunk0616.gpshealthconnect.data.repository.AccountRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AccountRepository {
    override val isAuthorized: Boolean
        get() = false

    override suspend fun login() {
        Log.d("AccountRepositoryImpl", "login: $email, $password")
        auth.signInAnonymously().await()
    }
}
