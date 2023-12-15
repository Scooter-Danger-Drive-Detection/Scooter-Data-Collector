package com.scooter.datacollector.domain.sensors

import com.scooter.datacollector.domain.models.AccelerationData

interface IAccelerometer {
    public fun getAccelerationData() : AccelerationData
}