package com.scooter.datacollector.domain.sensors

import com.scooter.datacollector.domain.models.RotationData

interface IGyroscope {
    public fun getRotationData(): RotationData
}