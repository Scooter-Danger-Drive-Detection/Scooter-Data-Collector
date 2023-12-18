package com.scooter.datacollector.data.local.entities

import androidx.room.Entity


@Entity(primaryKeys = ["id", "userId"], tableName = "sessions")
data class SessionEntity(
    val id: Long,
    val userId: Long,
    val rideMode: RideMode
)