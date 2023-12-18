package com.scooter.datacollector.data.api

import com.scooter.datacollector.data.api.controllers.SessionController
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val ApiDi = module{
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://158.160.79.188:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory<SessionController> {
        get<Retrofit>().create(SessionController::class.java)
    }

}