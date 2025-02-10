package com.shunk0616.gpshealthconnect.domain.service.step

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.shunk0616.gpshealthconnect.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StepCountForegroundService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private lateinit var firestore: FirebaseFirestore
    private lateinit var authentication: FirebaseAuth

    // サービスが作られた時に呼ばれる
    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        firestore = FirebaseFirestore.getInstance()
        authentication = FirebaseAuth.getInstance()
        // センサーが利用可能であれば登録
        stepCounterSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // サービスが開始されたときに呼ばれる
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Foreground通知生成
        val notification = createNotification("歩数をトラッキング中...")

        // Foreground Service として開始
        startForeground(NOTIFICATION_ID, notification)

        // 終了しても再度起動しないなら START_NOT_STICKY,
        // 強制終了時に再度起動させたいなら START_STICKY などを選ぶ
        return START_STICKY
    }

    // Foregroundで表示する通知を生成
    private fun createNotification(content: String): Notification {
        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, NotificationHelper.CHANNEL_ID)
        } else {
            Notification.Builder(this)
        }
        // 通知タップ時にアプリを開く PendingIntent などを設定可能
        return notificationBuilder
            .setContentTitle("Step Counter Service")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            // 累計歩数
            val steps = event.values[0]

            // ドキュメント名として yyyy-MM-dd を作成
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateStr = dateFormat.format(Date())

            // 時刻を取得（ミリ秒まで含むフォーマット）
            val timeFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
            val timeStr = timeFormat.format(Date())

            // 配列に追加するオブジェクト
            val stepData = mapOf(
                "time" to timeStr,
                "step" to steps
            )

            // Firebase に保存（stepData 配列に追加）
            // 既存ドキュメントがない場合でも作成されるよう、set(...) + merge を使用
            val updates = hashMapOf<String, Any>(
                "stepData" to FieldValue.arrayUnion(stepData)
            )

            firestore.collection("raw")
                .document(authentication.currentUser?.uid ?: "")
                .collection("step")
                .document(dateStr)
                .set(updates, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d("StepCountForegroundService", "Step count successfully updated.")
                }
                .addOnFailureListener {
                    Log.e("StepCountForegroundService", "Failed to update step count: ${it.message}")
                }
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 必要なら実装
    }

    // サービスが破棄される時
    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val NOTIFICATION_ID = 1001
    }
}

object NotificationHelper {
    const val CHANNEL_ID = "step_counter_channel"
    private const val CHANNEL_NAME = "Step Counter Service"
    private const val CHANNEL_DESCRIPTION = "Track user steps in foreground"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}
