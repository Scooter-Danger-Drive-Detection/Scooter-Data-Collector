package com.scooter.datacollector.sensors.gps

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.scooter.datacollector.domain.models.GpsData
import com.scooter.datacollector.domain.sensors.IGps
import java.util.logging.Handler

class Gps(private val context: Context) : IGps, LocationListener {
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var gpsData: GpsData = GpsData(0.0, 0.0, 0.0)

    init {
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            throw Exception()
        if(ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED){
            android.os.Handler(context.mainLooper).post{
                locationManager.requestLocationUpdates(LocationManager.FUSED_PROVIDER, 0L, 0f, this)
            }
        }
        else{
            Toast.makeText(context, "Отсутсвует разрешение на использование GPS!", Toast.LENGTH_SHORT).show();
            throw Exception("No GPS permission")
        }
    }

    override fun onLocationChanged(location: Location) {
        gpsData = GpsData(location.speed.toDouble(), location.latitude, location.longitude)
    }

    override fun getGpsData() = gpsData
}