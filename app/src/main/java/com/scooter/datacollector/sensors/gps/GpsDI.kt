package com.scooter.datacollector.sensors.gps

import com.scooter.datacollector.domain.sensors.IGps
import org.koin.dsl.module

var GpsDI = module {
    single<IGps> { Gps(get()) }
}