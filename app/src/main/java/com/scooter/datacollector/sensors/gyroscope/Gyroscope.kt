package com.scooter.datacollector.sensors.gyroscope

import android.content.Context
import com.scooter.datacollector.domain.models.RotationData
import com.scooter.datacollector.domain.sensors.IGyroscope
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class Gyroscope(private val context: Context) : IGyroscope, SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var previousData = RotationData(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    private var rotationData = RotationData(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

    init {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            rotationData = RotationData(rotationData.rotationDeltaX, rotationData.rotationDeltaY, rotationData.rotationDeltaZ,
                event.values[0].toDouble(), event.values[1].toDouble(), event.values[2].toDouble())
        }
        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            rotationData = RotationData(event.values[0].toDouble(), event.values[1].toDouble(), event.values[2].toDouble(),
                                        rotationData.angleSpeedX, rotationData.angleSpeedY, rotationData.angleSpeedZ)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // handle accuracy changes
    }
    // return angle delta from last request and angle speed now
    override fun getRotationData(): RotationData {
        val result = RotationData(rotationData.rotationDeltaX - previousData.rotationDeltaX,
                                  rotationData.rotationDeltaY - previousData.rotationDeltaY,
                                  rotationData.rotationDeltaZ - previousData.rotationDeltaZ,
                                              rotationData.angleSpeedX, rotationData.angleSpeedY, rotationData.angleSpeedZ)
        previousData = rotationData;
        return result;
    }
}