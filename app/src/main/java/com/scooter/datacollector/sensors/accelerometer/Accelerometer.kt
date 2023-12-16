package com.scooter.datacollector.sensors.accelerometer

import com.scooter.datacollector.domain.models.AccelerationData
import com.scooter.datacollector.domain.sensors.IAccelerometer
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class Accelerometer(private val sensorManager: SensorManager) : IAccelerometer, SensorEventListener {
    private var accelerationData = AccelerationData(0.0, 0.0, 0.0)

    init {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            accelerationData = AccelerationData(event.values[0].toDouble(), event.values[1].toDouble(), event.values[2].toDouble())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // handle accuracy changes
    }

    override fun getAccelerationData(): AccelerationData {
        return accelerationData
    }
}
