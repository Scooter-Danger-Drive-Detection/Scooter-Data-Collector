package com.scooter.datacollector.data.local.entities

import androidx.room.Entity

@Entity(primaryKeys = ["id", "sessionId"], tableName = "frames")
data class FrameEntity(
    val id: Int,
    val sessionId: Long,
    val previousFrameId: Int,
    val time: Long,
    val speed: Double,
    val latitude: Double,
    val longitude: Double,
    val linearAccelerationX: Double,
    val linearAccelerationY: Double,
    val linearAccelerationZ: Double,
    val gravityAccelerationX: Double,
    val gravityAccelerationY: Double,
    val gravityAccelerationZ: Double,
    val rotationDeltaMatrixField0: Float,
    val rotationDeltaMatrixField1: Float,
    val rotationDeltaMatrixField2: Float,
    val rotationDeltaMatrixField3: Float,
    val rotationDeltaMatrixField4: Float,
    val rotationDeltaMatrixField5: Float,
    val rotationDeltaMatrixField6: Float,
    val rotationDeltaMatrixField7: Float,
    val rotationDeltaMatrixField8: Float,
    val angleSpeedX: Double,
    val angleSpeedY: Double,
    val angleSpeedZ: Double,
)