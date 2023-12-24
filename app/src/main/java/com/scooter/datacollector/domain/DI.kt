package com.scooter.datacollector.domain

import com.scooter.datacollector.domain.usecases.CheckSessionStateUsecase
import com.scooter.datacollector.domain.usecases.EndSessionUsecase
import com.scooter.datacollector.domain.usecases.FrameUpdateUsecase
import com.scooter.datacollector.domain.usecases.GetRideSafetyUsecase
import com.scooter.datacollector.domain.usecases.StartSessionUsecase
import com.scooter.datacollector.domain.usecases.SynchronizeAllUsecase
import com.scooter.datacollector.domain.usecases.SyncronizeSessionUsecase
import org.koin.dsl.module

val DomainDI  = module {
    factory<CheckSessionStateUsecase> { CheckSessionStateUsecase(get()) }
    factory<EndSessionUsecase> { EndSessionUsecase(get()) }
    factory<FrameUpdateUsecase> { FrameUpdateUsecase(get()) }
    factory<StartSessionUsecase> { StartSessionUsecase(get()) }
    factory<GetRideSafetyUsecase> { GetRideSafetyUsecase(get(), get()) }
    factory<SynchronizeAllUsecase> { SynchronizeAllUsecase(get()) }
    factory<SyncronizeSessionUsecase> { SyncronizeSessionUsecase(get()) }
}