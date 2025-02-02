package com.shunk0616.gpshealthconnect.data.repository

import android.Manifest
import android.content.Context
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.shunk0616.gpshealthconnect.GhcActivity

interface GpsRepository {
    fun saveLocation(longitude: Double, latitude: Double)
}