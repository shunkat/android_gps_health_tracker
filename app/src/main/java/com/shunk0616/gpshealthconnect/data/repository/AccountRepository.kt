package com.shunk0616.gpshealthconnect.data.repository

interface AccountRepository {
    val isAuthorized: Boolean

    suspend fun login()
}
