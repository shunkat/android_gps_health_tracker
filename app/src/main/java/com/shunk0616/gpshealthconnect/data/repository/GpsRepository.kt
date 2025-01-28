package com.shunk0616.gpshealthconnect.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shunk0616.gpshealthconnect.GhcActivity

class GpsRepository(private val context: Context) {
    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    interface MyLocationCallback {
        fun onLocationResult(location: Location?)
        fun onLocationError(error: String)
    }

    fun getLastLocation(callback: MyLocationCallback) {
        // 最初に権限をチェック
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != 0
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != 0) {
            if (context is GhcActivity) {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1000
                )
            }
            return
        }
        requestLocation(callback)
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation(callback: GpsRepository.MyLocationCallback) {
        // 位置情報の取得
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    callback.onLocationResult(location)
                } else {
                    callback.onLocationError("Location is null")
                }
            }
            .addOnFailureListener { exception ->
                callback.onLocationError(exception.message ?: "Unknown error")
            }
    }
}