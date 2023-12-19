package com.scooter.datacollector.data.api.models

data class Frame(
    val FrameID: Int,
    val SessionID: Long,
    val PreviousFrameID: Long,
    val Time: Long,
    val GPS: Gps,
    val Accelerometer: Accelerometer,
    val Gyroscope: Gyroscope
)