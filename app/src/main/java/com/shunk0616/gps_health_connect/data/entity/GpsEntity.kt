package com.shunk0616.gps_health_connect.data.entity

data class GpsEntity(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double?,
    val timestamp: Long // Unix timeに対応するため
)