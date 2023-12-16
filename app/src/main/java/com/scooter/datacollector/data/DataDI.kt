package com.scooter.datacollector.data

import com.scooter.datacollector.domain.repositories.IFrameRepository
import com.scooter.datacollector.domain.repositories.ISessionRepository
import org.koin.dsl.module

val DataDI = module {
    single<IFrameRepository> { FrameRepository() }
    single<ISessionRepository> { SessionRepository() }
}
