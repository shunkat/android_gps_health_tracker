package com.shunk0616.gpshealthconnect.ui.setting

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun SettingRoute(onBackClick: () -> Unit) {
    SettingScreen(onBackClick = onBackClick)
}

@Composable
fun SettingScreen(onBackClick: () -> Unit) {
    Text(text = "Setting Screen")
    Button(
        onClick = {
            onBackClick()
        }
    ) {
        Text(text = "戻る")
    }
}
