package com.shunk0616.gpshealthconnect

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shunk0616.gpshealthconnect.domain.service.gps.GPSForegroundService
import com.shunk0616.gpshealthconnect.domain.service.gps.GPSLocationManager
import com.shunk0616.gpshealthconnect.domain.service.step.NotificationHelper
import com.shunk0616.gpshealthconnect.domain.service.step.StepCountForegroundService
import com.shunk0616.gpshealthconnect.ui.GhcApp
import com.shunk0616.gpshealthconnect.ui.rememberGhcAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GhcActivity : AppCompatActivity() {

    // TODO: Could be injected by DI
    private lateinit var gpsLocationManager: GPSLocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the notification channel (required if targeting Android 8.0+)
        NotificationHelper.createNotificationChannel(this)

        // Initialize location manager
        gpsLocationManager = GPSLocationManager(this)

        // Enable edge-to-edge if desired
        enableEdgeToEdge()

        // パーミッションチェック＆リクエスト
        checkAndRequestAllPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        gpsLocationManager.stopLocationUpdates()
        stopGPSService()
        stopStepService()
    }

    /**
     * 全パーミッションをチェックし、まだ許可されていないものがあればリクエストを行う。
     * 全て許可済みであれば直接 afterAllPermissionsGranted() を呼ぶ。
     */
    private fun checkAndRequestAllPermissions() {
        val permissionsNeeded = mutableListOf<String>()

        // ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // ACCESS_COARSE_LOCATION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        // POST_NOTIFICATIONS (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // ACTIVITY_RECOGNITION (Android 10+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.ACTIVITY_RECOGNITION)
            }
        }

        // リクエストすべきパーミッションがあればリクエストする
        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsNeeded.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // 全て許可されていれば実行
            afterAllPermissionsGranted()
        }
    }

    /**
     * パーミッションリクエスト結果を受け取るコールバック。
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // 全て許可されたかどうかを判定
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // 全て許可
                afterAllPermissionsGranted()
            } else {
                // いずれかのパーミッションが拒否されたときの処理 (必要に応じて実装)
                // 例: トースト表示やダイアログ表示など
            }
        }
    }

    /**
     * パーミッションが全て許可されたタイミングで呼び出される処理。
     * サービスの起動と setContent を行う。
     */
    private fun afterAllPermissionsGranted() {
        // サービスの起動
        startGPSService()
        startStepService()

        // UI のセットアップ
        setContent {
            Scaffold(modifier = androidx.compose.ui.Modifier.fillMaxSize()) { innerPadding ->
                GhcApp(
                    appState = rememberGhcAppState(),
                    modifier = androidx.compose.ui.Modifier.padding(innerPadding),
                    gpsLocationManager = gpsLocationManager
                )
            }
        }
    }

    /**
     * GPS フォアグラウンドサービスの開始。
     */
    private fun startGPSService() {
        val intent = Intent(this, GPSForegroundService::class.java)
        startService(intent)
    }

    /**
     * GPS フォアグラウンドサービスの停止。
     */
    private fun stopGPSService() {
        val intent = Intent(this, GPSForegroundService::class.java)
        stopService(intent)
    }

    /**
     * 歩数計測フォアグラウンドサービスの開始。
     */
    private fun startStepService() {
        val intent = Intent(this, StepCountForegroundService::class.java)
        startService(intent)
    }

    /**
     * 歩数計測フォアグラウンドサービスの停止。
     */
    private fun stopStepService() {
        val intent = Intent(this, StepCountForegroundService::class.java)
        stopService(intent)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1000
    }
}
