package com.scooter.datacollector.domain.sensors

import com.scooter.datacollector.domain.models.GpsData

interface IGps {
    public fun getGpsData() : GpsData

}