package com.shunk0616.gpshealthconnect.domain.service.gps

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.shunk0616.gpshealthconnect.GhcActivity
import com.shunk0616.gpshealthconnect.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GPSForegroundService: Service() {
    private lateinit var gpsLocationManager: GPSLocationManager
    private lateinit var firestore: FirebaseFirestore
    private lateinit var authentication: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        firestore = FirebaseFirestore.getInstance()
        authentication = FirebaseAuth.getInstance()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        gpsLocationManager = GPSLocationManager(this)
        gpsLocationManager.startLocationUpdates(object : GPSLocationManager.MyLocationCallback {
            override fun onLocationResult(location: Location?) {
                location?.let { loc ->
                    // ここで Firestore への保存を行う
                    val user = authentication.currentUser
                    if (user != null) {
                        val userId = user.uid
                        // ドキュメントID用に当日の日付(yyyy-MM-dd)を生成
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val dateString = dateFormat.format(Date())

                        // 保存するオブジェクト (lat, lng, time)
                        val locationData = hashMapOf(
                            "lat" to loc.latitude,
                            "lng" to loc.longitude,
                            "time" to System.currentTimeMillis()
                        )

                        // ドキュメント参照: raw > userId > gps > yyyy-MM-dd
                        val docRef = firestore.collection("raw")
                            .document(userId)
                            .collection("gps")
                            .document(dateString)

                        // locations という配列フィールドを更新し、オブジェクトを追加
                        docRef.update("locations", FieldValue.arrayUnion(locationData))
                            .addOnSuccessListener {
                                Log.d("GPSForegroundService", "Location successfully appended.")
                            }
                            .addOnFailureListener { e ->
                                // ドキュメントが存在しない場合などは set で新規作成する
                                docRef.set(mapOf("locations" to listOf(locationData)))
                                    .addOnSuccessListener {
                                        Log.d("GPSForegroundService", "New document created with initial location data.")
                                    }
                                    .addOnFailureListener { ex ->
                                        Log.e("GPSForegroundService", "Failed to create document: ${ex.message}")
                                    }
                            }
                    } else {
                        Log.e("GPSForegroundService", "No authenticated user found.")
                    }
                }
            }

            override fun onLocationError(error: String) {
                Log.e("GPSForegroundService", "Location Error: $error")
            }
        })
        startForegroundServiceWithNotification()
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startForegroundServiceWithNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "GPSServiceChannel"
        val channelName = "GPS Service Channel"

        // 通知チャンネルを作成
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)

        // メインアクティビティを起動するPendingIntent
        val notificationIntent = Intent(this, GhcActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        // 通知の作成
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("GPS 位置情報サービス")
            .setContentText("位置情報を取得しています")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        // フォアグラウンドサービスとして通知を表示
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        // GPSの位置情報取得を停止
        gpsLocationManager.stopLocationUpdates()
        stopForeground(true) // 通知の削除
        stopSelf() // サービスの停止
    }

    override fun onBind(intent: Intent?) = null
}