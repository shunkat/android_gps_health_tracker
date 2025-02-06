package com.shunk0616.gpshealthconnect.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.shunk0616.gpshealthconnect.data.repository.AccountRepository
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class AccountRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AccountRepository {
    override val isAuthorized: Boolean
        get() = auth.currentUser != null

    override suspend fun login() {
        auth.signInAnonymously().await()
    }
}
