package com.scooter.datacollector.domain.models

data class Session(
    val id: Int,
    val usedId: Int,
    val rideMode: RideMode,
)
