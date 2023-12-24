package com.scooter.datacollector.data.api

import com.scooter.datacollector.data.RideSafety
import com.scooter.datacollector.data.api.controllers.RideSafetyController
import com.scooter.datacollector.data.api.controllers.SessionController
import com.scooter.datacollector.domain.IRideSafety
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val ApiDi = module{
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("http://78.40.219.245:8080")
            .client(
                OkHttpClient.Builder().addInterceptor(
                    HttpLoggingInterceptor()//.setLevel(HttpLoggingInterceptor.Level.BODY)
                ).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory<SessionController> {
        get<Retrofit>().create(SessionController::class.java)
    }

    factory<RideSafetyController> {
        get<Retrofit>().create(RideSafetyController::class.java)
    }

    factory<IRideSafety> { RideSafety(get()) }
}