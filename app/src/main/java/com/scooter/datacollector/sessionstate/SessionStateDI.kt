package com.scooter.datacollector.sessionstate

import com.scooter.datacollector.domain.ISessionController
import org.koin.dsl.module

val SessionStateDI = module{
    single<ISessionController> { SessionController(get(), get(), get()) }
}