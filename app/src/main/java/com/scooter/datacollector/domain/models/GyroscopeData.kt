package com.scooter.datacollector.domain.models

data class GyroscopeData(
    val rotationDelta: FloatArray,
    val angleSpeedX: Double,
    val angleSpeedY: Double,
    val angleSpeedZ: Double
)