package com.shunk0616.gps_health_connect.data.source.gps

data class Gps(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double?,
    val timestamp: Long,
)