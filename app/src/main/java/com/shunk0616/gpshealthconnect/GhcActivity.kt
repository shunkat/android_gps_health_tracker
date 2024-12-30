package com.shunk0616.gpshealthconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import com.shunk0616.gpshealthconnect.ui.GhcApp
import com.shunk0616.gpshealthconnect.ui.common.theme.GhcTheme
import com.shunk0616.gpshealthconnect.ui.rememberGhcAppState

class GhcActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberGhcAppState()
            GhcTheme {
                GhcApp(appState, modifier = Modifier)
            }
        }
    }
}
