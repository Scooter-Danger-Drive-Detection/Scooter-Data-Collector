package com.scooter.datacollector.domain.models

data class Frame(
    val frameId : Int,
    val sessionId: Int,
    val lastFrameId: Int,
    val time: Long,
    val gps : GpsData,
    val accelerometer : AccelerationData,
    val gyroscopeData: GyroscopeData
)
