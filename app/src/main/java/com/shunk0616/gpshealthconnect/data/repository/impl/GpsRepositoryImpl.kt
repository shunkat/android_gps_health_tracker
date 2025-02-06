package com.shunk0616.gpshealthconnect.data.repository.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.shunk0616.gpshealthconnect.data.repository.GpsRepository
import javax.inject.Inject


class GpsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : GpsRepository {
    override fun saveLocation(latitude: Double, longitude: Double) {
        firestore.collection("gps").add(
            hashMapOf(
                "latitude" to latitude,
                "longitude" to longitude,
                "timestamp" to System.currentTimeMillis()
            )
        )
    }
}
