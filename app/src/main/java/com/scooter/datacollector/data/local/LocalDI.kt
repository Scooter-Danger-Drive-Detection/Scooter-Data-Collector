package com.scooter.datacollector.data.local

import androidx.room.Room
import org.koin.dsl.module

val LocalDI = module {
    single<LocalDatabase>{ Room.databaseBuilder(get(), LocalDatabase::class.java, "localData").build() }
}