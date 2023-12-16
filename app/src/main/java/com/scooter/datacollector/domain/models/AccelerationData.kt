package com.scooter.datacollector.domain.models

data class AccelerationData(
    val linearAccelerationX: Double,
    val linearAccelerationY: Double,
    val linearAccelerationZ: Double,
    val gravityAccelerationX: Double,
    val gravityAccelerationY: Double,
    val gravityAccelerationZ: Double,
)
