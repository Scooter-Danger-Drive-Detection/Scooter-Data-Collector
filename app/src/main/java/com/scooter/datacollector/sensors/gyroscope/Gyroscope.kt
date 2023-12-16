package com.scooter.datacollector.sensors.gyroscope

import android.content.Context
import android.hardware.SensorManager
import com.scooter.datacollector.domain.models.GyroscopeData
import com.scooter.datacollector.domain.sensors.IGyroscope

class Gyroscope(private val context: Context) : IGyroscope {
    private val angleSpeedSensor: AngleSpeedSensor = AngleSpeedSensor(context)

    override fun getRotationData(): GyroscopeData {
        val angleSpeedData = angleSpeedSensor.getAngleSpeedData()
        val rotationDelta = angleSpeedSensor.getRotationData()

        return GyroscopeData(
            rotationDelta = rotationDelta.deltaRotationMatrix,
            angleSpeedX = angleSpeedData.angleSpeedX,
            angleSpeedY = angleSpeedData.angleSpeedY,
            angleSpeedZ = angleSpeedData.angleSpeedZ,
        )
    }
}