package com.shunk0616.gpshealthconnect.data.repository.impl

import com.shunk0616.gpshealthconnect.data.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
//    private val auth: FirebaseAuth
) : AccountRepository {
    override val isAuthorized: Boolean
        get() = false
}
