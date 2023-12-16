package com.scooter.datacollector.sessionstate

import org.koin.dsl.module

val SessionStateDI = module{
    single{
        SessionController(get(), get())
    }
}