package com.shunk0616.gps_health_connect.data.source.health

import java.time.Instant

// 基底クラス：すべてのヘルスデータエンティティが継承
open class Health(
    val id: String,
    val startTime: Instant,
    val endTime: Instant,
    val sourceApp: String,
)
