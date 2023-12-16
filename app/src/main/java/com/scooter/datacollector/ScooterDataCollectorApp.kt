package com.scooter.datacollector

import android.app.Application
import com.scooter.datacollector.auth.AuthDI
import com.scooter.datacollector.data.DataDI
import com.scooter.datacollector.domain.DomainDI
import com.scooter.datacollector.framecollector.FrameCollectorDI
import com.scooter.datacollector.presentation.PresentationDI
import com.scooter.datacollector.sensors.SensorsDI
import com.scooter.datacollector.sessionstate.SessionStateDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ScooterDataCollectorApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@ScooterDataCollectorApp)
            modules(
                AuthDI,
                DataDI,
                DomainDI,
                FrameCollectorDI,
                PresentationDI,
                SensorsDI,
                SessionStateDI
            )
        }
    }
}