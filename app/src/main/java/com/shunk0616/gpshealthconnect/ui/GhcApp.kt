package com.shunk0616.gpshealthconnect.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.shunk0616.gpshealthconnect.domain.service.gps.GPSLocationManager
import com.shunk0616.gpshealthconnect.navigation.GhcNavHost

@Composable
fun GhcApp(appState: GhcAppState, modifier: Modifier = Modifier, gpsLocationManager: GPSLocationManager) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        GhcAppInternal(
            appState = appState,
            snackbarHostState = snackbarHostState,
            modifier = Modifier.fillMaxSize(),
            gpsLocationManager = gpsLocationManager
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GhcAppInternal(
    appState: GhcAppState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    gpsLocationManager: GPSLocationManager
) {
    Scaffold(
        modifier = modifier.testTag("GhcScaffold"),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(Modifier.weight(1f)) {
                GhcNavHost(
                    appState = appState,
                    onShowSnackbar = { message, action ->
                        snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = action,
                            duration = SnackbarDuration.Short
                        ) == SnackbarResult.ActionPerformed
                    },
                    modifier = Modifier.fillMaxSize(),
                    gpsLocationManager = gpsLocationManager
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyTopBar(onActionClick: () -> Unit) {
    androidx.compose.material3.TopAppBar(
        title = {
            Text(text = "GHC App")
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        actions = {
            // 設定アイコンなど
            // IconButton(onClick = onActionClick) { Icon(...) }
        }
    )
}
