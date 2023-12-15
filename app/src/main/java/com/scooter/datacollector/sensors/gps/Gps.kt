package com.scooter.datacollector.sensors.gps

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import com.scooter.datacollector.domain.models.GpsData
import com.scooter.datacollector.domain.sensors.IGps

class Gps(private val context: Context) : IGps, LocationListener {
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var gpsData: GpsData = GpsData(0.0, 0.0, 0.0)

    init {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 0f, this)
        } catch(ex: SecurityException) {
            Toast.makeText(context, "Отсутсвует разрешение на использование GPS!", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onLocationChanged(location: Location) {
        gpsData = GpsData(location.speed.toDouble(), location.latitude, location.longitude)
    }

    override fun getGpsData() = gpsData
}