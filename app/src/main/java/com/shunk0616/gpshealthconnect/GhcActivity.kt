package com.shunk0616.gpshealthconnect

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shunk0616.gpshealthconnect.domain.service.gps.GPSForegroundService
import com.shunk0616.gpshealthconnect.domain.service.gps.GPSLocationManager
import com.shunk0616.gpshealthconnect.domain.service.step.NotificationHelper
import com.shunk0616.gpshealthconnect.domain.service.step.StepCountForegroundService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GhcActivity : AppCompatActivity() {

    // TODO: Could be injected by DI
    private lateinit var gpsLocationManager: GPSLocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the notification channel (required if targeting Android 8.0+)
        NotificationHelper.createNotificationChannel(this)

        // Initialize location manager
        gpsLocationManager = GPSLocationManager(this)

        // Enable edge-to-edge if desired
        enableEdgeToEdge()

        // Check and request all required permissions in one place
        checkAndRequestAllPermissions()

        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                GPSViewer(
                    modifier = Modifier.padding(innerPadding),
                    gpsLocationManager = gpsLocationManager
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gpsLocationManager.stopLocationUpdates()
        stopGPSService()
        stopStepService()
    }

    /**
     * Build the list of required permissions and request them if they're not granted.
     */
    private fun checkAndRequestAllPermissions() {
        val permissionsNeeded = mutableListOf<String>()

        // ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // ACCESS_COARSE_LOCATION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        // POST_NOTIFICATIONS (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // ACTIVITY_RECOGNITION (Android 10+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.ACTIVITY_RECOGNITION)
            }
        }

        // If there are any permissions not granted, request them
        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsNeeded.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // All permissions are already granted; proceed with your logic
            startGPSService()
            startStepService()
        }
    }

    /**
     * Callback that listens for the result of permission requests.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Check if all permissions are granted
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startGPSService()
                startStepService()
            } else {
                // Some permission was denied; handle gracefully
                // For example, show a dialog or disable related features
            }
        }
    }

    /**
     * Start the GPS foreground service.
     */
    private fun startGPSService() {
        val intent = Intent(this, GPSForegroundService::class.java)
        startService(intent)
    }

    /**
     * Stop the GPS foreground service.
     */
    private fun stopGPSService() {
        val intent = Intent(this, GPSForegroundService::class.java)
        stopService(intent)
    }

    /**
     * Start the step-count foreground service.
     */
    private fun startStepService() {
        val intent = Intent(this, StepCountForegroundService::class.java)
        startService(intent)
    }

    /**
     * Stop the step-count foreground service.
     */
    private fun stopStepService() {
        val intent = Intent(this, StepCountForegroundService::class.java)
        stopService(intent)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1000
    }
}

@Composable
fun GPSViewer(
    modifier: Modifier = Modifier,
    gpsLocationManager: GPSLocationManager
) {
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Latitude: $latitude\nLongitude: $longitude",
            modifier = modifier
        )
        Button(
            onClick = {
                gpsLocationManager.startLocationUpdates(object : GPSLocationManager.MyLocationCallback {
                    override fun onLocationResult(location: Location?) {
                        if (location != null) {
                            latitude = location.latitude.toString()
                            longitude = location.longitude.toString()
                        }
                    }

                    override fun onLocationError(error: String) {
                        // Handle error here
                    }
                })
            }
        ) {
            Text(text = "Get Location")
        }
    }
}
