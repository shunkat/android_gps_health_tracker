package com.shunk0616.gpshealthconnect.ui.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun HomeRoute(
    onSettingClick: () -> Unit,
) {
    HomeScreen(
        onSettingClick = onSettingClick
    )
}

@Composable
fun HomeScreen(
    onSettingClick: () -> Unit
) {
    Text(text = "Home Screen")
    Button(onClick = onSettingClick) {
        Text(text = "Click me")
    }
}
