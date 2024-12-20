package com.shunk0616.gpshealthconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shunk0616.gps_health_connect.ui.GpsHealthConnectApp

class GpsHealthConnectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GpsHealthConnectApp()
        }
    }
}
