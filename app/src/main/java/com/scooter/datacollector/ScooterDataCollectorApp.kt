package com.scooter.datacollector

import android.app.Application
import com.scooter.datacollector.data.DataDI
import com.scooter.datacollector.domain.DomainDI
import com.scooter.datacollector.presentation.PresentationDI
import com.scooter.datacollector.sensors.SensorsDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ScooterDataCollectorApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@ScooterDataCollectorApp)
            modules(DataDI, SensorsDI, DomainDI, PresentationDI)
        }
    }
}