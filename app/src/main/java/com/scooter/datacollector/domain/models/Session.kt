package com.scooter.datacollector.domain.models

data class Session(
    val id: Long,
    val userId: Long,
    val rideMode: RideMode,
)
