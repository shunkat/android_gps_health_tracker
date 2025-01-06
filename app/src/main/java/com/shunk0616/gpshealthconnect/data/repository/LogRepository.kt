package com.shunk0616.gpshealthconnect.data.repository

interface LogRepository {
    fun logNonFatalCrash(thromwable: Throwable)
}