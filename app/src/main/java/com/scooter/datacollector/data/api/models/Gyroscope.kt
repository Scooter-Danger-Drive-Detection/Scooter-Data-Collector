package com.scooter.datacollector.data.api.models

data class Gyroscope(
    val RotationDeltaMatrix: FloatArray,
    val AngleSpeedX: Double,
    val AngleSpeedY: Double,
    val AngleSpeedZ: Double,
)