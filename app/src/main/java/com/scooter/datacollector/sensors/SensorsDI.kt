package com.scooter.datacollector.sensors

import com.scooter.datacollector.sensors.accelerometer.AccelerometerDI
import com.scooter.datacollector.sensors.gps.GpsDI
import com.scooter.datacollector.sensors.gyroscope.GyroscopeDI
import org.koin.dsl.module

val SensorsDI = module {
    includes(AccelerometerDI, GpsDI, GyroscopeDI)
}