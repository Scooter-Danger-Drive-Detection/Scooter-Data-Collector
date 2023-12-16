package com.scooter.datacollector.framecollector

import com.scooter.datacollector.domain.IFrameDataCollector
import org.koin.dsl.module

val FrameCollectorDI = module {
    single<IFrameDataCollector> { FrameDataCollector(get(), get(), get(), get(), get()) }
}