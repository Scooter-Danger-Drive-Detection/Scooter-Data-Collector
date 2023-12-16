package com.scooter.datacollector.sensors.accelerometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class LinearAccelerometerSensor(private val context: Context) : SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var accelerationData = LinearAccelerationData(0.0, 0.0, 0.0)

    init {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            accelerationData = LinearAccelerationData(event.values[0].toDouble(), event.values[1].toDouble(), event.values[2].toDouble())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // handle accuracy changes
    }

    fun getAccelerationData(): LinearAccelerationData = accelerationData
}