package com.scooter.datacollector.domain

import com.scooter.datacollector.domain.usecases.CheckSessionStateUsecase
import com.scooter.datacollector.domain.usecases.EndSessionUsecase
import com.scooter.datacollector.domain.usecases.FrameUpdateUsecase
import com.scooter.datacollector.domain.usecases.StartSessionUsecase
import org.koin.dsl.module

val DomainDI  = module {
    factory<CheckSessionStateUsecase> { CheckSessionStateUsecase(get()) }
    factory<EndSessionUsecase> { EndSessionUsecase(get()) }
    factory<FrameUpdateUsecase> { FrameUpdateUsecase(get()) }
    factory<StartSessionUsecase> { StartSessionUsecase(get()) }
}