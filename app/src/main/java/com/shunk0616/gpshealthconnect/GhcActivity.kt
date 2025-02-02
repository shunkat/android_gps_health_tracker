package com.shunk0616.gpshealthconnect

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.shunk0616.gpshealthconnect.domain.service.GPSForegroundService
import com.shunk0616.gpshealthconnect.domain.service.GPSLocationManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GhcActivity : ComponentActivity() {
    // TODO: DIに組み込もう
    private lateinit var gpsLocationManager: GPSLocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gpsLocationManager = GPSLocationManager(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            // 必要な場合、パーミッションを要求
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.POST_NOTIFICATIONS), 1000)
        }else {
            // 権限が既にある場合はサービスを開始する
            startGPSService()
        }

        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                GPSViewer(
                    modifier = Modifier.padding(innerPadding),
                    gpsLocationManager = gpsLocationManager
                )
            }
//            val appState = rememberGhcAppState()
//            GhcTheme {
//                GhcApp(appState, modifier = Modifier)
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gpsLocationManager.stopLocationUpdates()

        stopGPSService()
    }

    private fun startGPSService() {
        val intent = Intent(this, GPSForegroundService::class.java)
        startService(intent) // サービスの起動
    }

    private fun stopGPSService() {
        val intent = Intent(this, GPSForegroundService::class.java)
        stopService(intent) // サービスの停止
    }
}

@Composable
fun GPSViewer(modifier: Modifier = Modifier, gpsLocationManager: GPSLocationManager) {
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "緯度:$latitude\n経度:$longitude",
            modifier = modifier
        )
        Button(onClick = {
            gpsLocationManager.startLocationUpdates(object : GPSLocationManager.MyLocationCallback {
                override fun onLocationResult(location: Location?) {
                    if (location != null) {
                        latitude = location.latitude.toString()
                        longitude = location.longitude.toString()
                    }
                }

                override fun onLocationError(error: String) {
                }
            })
        }) {
            Text(
                text = "位置情報取得"
            )
        }
    }
}
