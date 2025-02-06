package com.shunk0616.gpshealthconnect.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class GpsRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun saveLocation(latitude: Double, longitude: Double) {
        firestore.collection("gps")
            .add(
                hashMapOf(
                    "latitude" to latitude,
                    "longitude" to longitude
                )
            )
            .addOnSuccessListener {
                println("Location saved")
            }
            .addOnFailureListener {
                println("Error saving location")
            }
    }
}