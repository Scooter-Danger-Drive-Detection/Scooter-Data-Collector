package com.scooter.datacollector.data

import com.scooter.datacollector.data.local.LocalDI
import com.scooter.datacollector.data.local.LocalDatabase
import com.scooter.datacollector.domain.repositories.IFrameRepository
import com.scooter.datacollector.domain.repositories.ISessionRepository
import org.koin.dsl.module

val DataDI = module {
    includes(LocalDI)
    single<IFrameRepository> { FrameRepository(get()) }
    single<ISessionRepository> { SessionRepository(get()) }
}
