package com.shunk0616.gpshealthconnect

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
import com.shunk0616.gpshealthconnect.data.repository.GpsRepository
import com.shunk0616.gpshealthconnect.ui.GhcApp
import com.shunk0616.gpshealthconnect.ui.common.theme.GhcTheme
import com.shunk0616.gpshealthconnect.ui.rememberGhcAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GhcActivity : ComponentActivity() {
    // TODO:
    //  DIに組み込もう
    private lateinit var gpsRepository: GpsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gpsRepository = GpsRepository(this)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                GPSViewer(
                    modifier = Modifier.padding(innerPadding),
                    gpsRepository = gpsRepository
                )

            }
//            val appState = rememberGhcAppState()
//            GhcTheme {
//                GhcApp(appState, modifier = Modifier)
//            }
        }
    }
}


@Composable
fun GPSViewer(modifier: Modifier = Modifier,gpsRepository: GpsRepository) {
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "緯度:$latitude\n経度:$longitude",
            modifier = modifier
        )
        Button(onClick = {
            gpsRepository.getLastLocation(object : GpsRepository.MyLocationCallback {
                override fun onLocationResult(location: Location?) {
                    if (location != null) {
                        latitude = location.latitude.toString()
                        longitude = location.longitude.toString()
                    }
                }

                override fun onLocationError(error: String) {
                    // エラー処理
                }
            })
        }) {
            Text(
                text = "位置情報取得",
            )
        }
    }
}