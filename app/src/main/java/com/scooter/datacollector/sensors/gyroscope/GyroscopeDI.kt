package com.scooter.datacollector.sensors.gyroscope

import com.scooter.datacollector.domain.sensors.IGyroscope
import org.koin.dsl.module

val GyroscopeDI = module{
    single<IGyroscope> { Gyroscope(get()) }
}