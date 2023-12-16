package com.scooter.datacollector.sensors.accelerometer

import android.content.Context
import com.scooter.datacollector.domain.models.AccelerationData
import com.scooter.datacollector.domain.sensors.IAccelerometer

class Accelerometer(private val context: Context) : IAccelerometer {
    private val accelerometerSensor: LinearAccelerometerSensor = LinearAccelerometerSensor(context)
    private val gravitySensor: GravitySensor = GravitySensor(context)

    override fun getAccelerationData(): AccelerationData = AccelerationData(
        linearAccelerationX = accelerometerSensor.getAccelerationData().linearAccelerationX,
        linearAccelerationY = accelerometerSensor.getAccelerationData().linearAccelerationY,
        linearAccelerationZ = accelerometerSensor.getAccelerationData().linearAccelerationZ,
        gravityAccelerationX = gravitySensor.getGravityData().gravityX,
        gravityAccelerationY = gravitySensor.getGravityData().gravityY,
        gravityAccelerationZ = gravitySensor.getGravityData().gravityZ,
    )
}
