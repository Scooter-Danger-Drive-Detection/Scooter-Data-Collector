package com.scooter.datacollector.domain

import com.scooter.datacollector.domain.usecases.CheckSessionStateUsecase
import org.koin.dsl.module

val DomainDI  = module {
    factory<CheckSessionStateUsecase> { CheckSessionStateUsecase(get()) }
}