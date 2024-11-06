package com.shunk0616.gps_health_connect.data.entity

import java.time.Instant

// 基底クラス：すべてのヘルスデータエンティティが継承
open class HealthDataEntity(
    val id: String,
    val startTime: Instant,
    val endTime: Instant,
    val sourceApp: String
)

// 歩数データエンティティ
data class StepCountEntity(
    val count: Long,
    val startTime: Instant,
    val endTime: Instant,
    val sourceApp: String
) : HealthDataEntity(id = "step_count_${startTime.toEpochMilli()}", startTime, endTime, sourceApp)

// 心拍数データエンティティ
data class HeartRateEntity(
    val bpm: Double,
    val startTime: Instant,
    val endTime: Instant,
    val sourceApp: String
) : HealthDataEntity(id = "heart_rate_${startTime.toEpochMilli()}", startTime, endTime, sourceApp)

// 体重データエンティティ
data class WeightEntity(
    val weightKg: Double,
    val timestamp: Instant,
    val sourceApp: String
) : HealthDataEntity(id = "weight_${timestamp.toEpochMilli()}", startTime = timestamp, endTime = timestamp, sourceApp)
