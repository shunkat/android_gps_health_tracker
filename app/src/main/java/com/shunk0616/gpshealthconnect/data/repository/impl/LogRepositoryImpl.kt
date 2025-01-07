package com.shunk0616.gpshealthconnect.data.repository.impl

import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.shunk0616.gpshealthconnect.data.repository.LogRepository
import javax.inject.Inject

class LogRepositoryImpl @Inject constructor() : LogRepository {
    override fun logNonFatalCrash(thromwable: Throwable) {
        Firebase.crashlytics.recordException(thromwable)
    }
}
