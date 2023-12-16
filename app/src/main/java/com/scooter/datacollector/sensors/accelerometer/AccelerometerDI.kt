package com.scooter.datacollector.sensors.accelerometer

import com.scooter.datacollector.domain.sensors.IAccelerometer
import org.koin.dsl.module

val AccelerometerDI = module {
    single<IAccelerometer> { Accelerometer() }
}