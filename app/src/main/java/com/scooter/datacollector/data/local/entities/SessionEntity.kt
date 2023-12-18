package com.scooter.datacollector.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scooter.datacollector.domain.models.RideMode

@Entity(primaryKeys = ["id", "userId"], tableName = "sessions")
data class SessionEntity(
    val id: Long,
    val userId: Long,
    val rideMode: RideMode
)