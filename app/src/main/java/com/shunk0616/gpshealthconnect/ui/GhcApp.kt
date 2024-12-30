package com.shunk0616.gpshealthconnect.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.shunk0616.gpshealthconnect.navigation.GhcNavHost
import com.shunk0616.gpshealthconnect.ui.home.navigation.HomeRoute
import com.shunk0616.gpshealthconnect.ui.setting.navigation.navigateToSetting

/**
 * アプリ全体を包む最上位Composable
 */
@Composable
fun GhcApp(
    appState: GhcAppState,  // アプリ特有の状態を管理すると想定
    modifier: Modifier = Modifier,
) {
    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        // 内部用のGhcAppを呼び出す
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
    val navController = rememberNavController()


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
                // メインの画面コンテンツ(画面ナビゲーション)
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
