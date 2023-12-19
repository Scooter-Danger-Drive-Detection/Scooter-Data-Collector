package com.scooter.datacollector.data.api

import com.scooter.datacollector.data.api.controllers.SessionController
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val ApiDi = module{
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("http://158.160.79.188:8000")
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

}