package com.scooter.datacollector.domain.sensors

import com.scooter.datacollector.domain.models.GyroscopeData

interface IGyroscope {
    public fun getRotationData(): GyroscopeData
}