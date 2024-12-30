package com.shunk0616.gpshealthconnect.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.navigation.compose.rememberNavController
import com.shunk0616.gpshealthconnect.navigation.GhcNavHost

@Composable
fun GhcApp(
    appState: GhcAppState,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        GhcAppInternal(
            appState = appState,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GhcAppInternal(
    appState: GhcAppState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.testTag("GhcScaffold"),
        containerColor = Color.Transparent,        // Scaffoldの背景を透明に
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(Modifier.weight(1f)) {
                GhcNavHost(
                    appState = appState,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyTopBar(
    onActionClick: () -> Unit,
) {
    androidx.compose.material3.TopAppBar(
        title = {
            Text(text = "GHC App")
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
        ),
        actions = {
            // 設定アイコンなど
            // IconButton(onClick = onActionClick) { Icon(...) }
        },
    )
}
