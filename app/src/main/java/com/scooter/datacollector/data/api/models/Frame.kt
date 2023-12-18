package com.scooter.datacollector.data.api.models

data class Frame(
    val ID: Id,
    val Time: Long,
    val GPS: Gps,
    val Accelerometer: Accelerometer,
    val Gyroscope: Gyroscope
)