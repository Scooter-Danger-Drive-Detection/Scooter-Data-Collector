package com.scooter.datacollector.data

import com.scooter.datacollector.data.api.ApiDi
import com.scooter.datacollector.data.local.LocalDI
import com.scooter.datacollector.data.local.LocalDatabase
import com.scooter.datacollector.domain.IDataSynchronizer
import com.scooter.datacollector.domain.repositories.IFrameRepository
import com.scooter.datacollector.domain.repositories.ISessionRepository
import org.koin.dsl.module

val DataDI = module {
    includes(LocalDI, ApiDi)
    single<IFrameRepository> { FrameRepository(get()) }
    single<ISessionRepository> { SessionRepository(get()) }
    factory<IDataSynchronizer> { DataSynchronizer(get(), get(), get()) }
}
