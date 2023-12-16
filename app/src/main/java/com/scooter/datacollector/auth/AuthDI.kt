package com.scooter.datacollector.auth

import com.scooter.datacollector.domain.auth.IAuth
import org.koin.dsl.module

val AuthDI = module {
    factory<IAuth> { Auth(get()) }
}