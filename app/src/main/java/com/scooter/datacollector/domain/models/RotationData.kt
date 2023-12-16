package com.scooter.datacollector.domain.models

data class RotationData(
    val rotationDeltaX: Double,
    val rotationDeltaY: Double,
    val rotationDeltaZ: Double,
    val angleSpeedX: Double,
    val angleSpeedY: Double,
    val angleSpeedZ: Double
)