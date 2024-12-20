package com.shunk0616.gps_health_connect.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shunk0616.gpshealthconnect.ui.auth.AuthenticationScreen
import com.shunk0616.gpshealthconnect.ui.common.theme.gpshealthconnectTheme

@Composable
fun GpsHealthConnectApp() {
    gpshealthconnectTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AuthenticationScreen()
        }
    }
}
