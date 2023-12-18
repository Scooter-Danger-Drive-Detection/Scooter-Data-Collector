package com.scooter.datacollector.domain.models

data class Session(
    val id: Long,
    val usedId: Long,
    val rideMode: RideMode,
)
