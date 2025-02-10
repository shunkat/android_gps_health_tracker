package com.shunk0616.gpshealthconnect.ui.home

import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shunk0616.gpshealthconnect.domain.service.gps.GPSLocationManager

// 時間の関係上一旦routeに渡されたものを。そのままscreenに渡す実装にする
@Composable
internal fun HomeRoute(gpsLocationManager: GPSLocationManager) {
    HomeScreen(
        modifier = Modifier,
        gpsLocationManager = gpsLocationManager
    )
}

@Composable
fun HomeScreen(modifier: Modifier, gpsLocationManager: GPSLocationManager) {
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var stepCount by remember { mutableStateOf("") }

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
            Text(text = "現在の位置情報をdebug表示する")
        }
    }
}
