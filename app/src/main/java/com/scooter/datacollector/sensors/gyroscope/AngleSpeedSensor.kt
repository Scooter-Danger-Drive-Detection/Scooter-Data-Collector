package com.scooter.datacollector.sensors.gyroscope

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class AngleSpeedSensor(private val context: Context) : SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private lateinit var angleSpeedData: AngleSpeedData
    private var rotationDelta: RotationData = RotationData(FloatArray(9){0f})

    private val NS2S = 1.0/1000000000.0
    private var timestamp: Long = 0

    init {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(timestamp == 0L){
            timestamp = event.timestamp
            return
        }

        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            angleSpeedData = AngleSpeedData(event.values[0].toDouble(), event.values[1].toDouble(), event.values[2].toDouble())

            val dT = ((event.timestamp - timestamp) * NS2S).toFloat()

            val omegaMagnitude: Float = sqrt(event.values[0] * event.values[0] + event.values[1] * event.values[1] + event.values[2] * event.values[2])

            // Normalize the rotation vector if it's big enough to get the axis
            // (that is, EPSILON should represent your maximum allowable margin of error)
            val EPSILON = 100
            if (omegaMagnitude > EPSILON) {
                event.values[0] /= omegaMagnitude
                event.values[1] /= omegaMagnitude
                event.values[2] /= omegaMagnitude
            }

            val deltaRotationVector = FloatArray(4) {0f}

            val thetaOverTwo: Float = omegaMagnitude * dT / 2.0f
            val sinThetaOverTwo: Float = sin(thetaOverTwo)
            val cosThetaOverTwo: Float = cos(thetaOverTwo)
            deltaRotationVector[0] = sinThetaOverTwo * event.values[0]
            deltaRotationVector[1] = sinThetaOverTwo * event.values[1]
            deltaRotationVector[2] = sinThetaOverTwo * event.values[2]
            deltaRotationVector[3] = cosThetaOverTwo
            timestamp = event.timestamp
            val deltaRotationMatrix = FloatArray(9) { 0f }
            SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector)

            for(i in 0..8){
                rotationDelta.deltaRotationMatrix[i] += deltaRotationMatrix[i]
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    public fun getAngleSpeedData() : AngleSpeedData = angleSpeedData
    public fun getRotationData() : RotationData{
        val ans = rotationDelta
        rotationDelta = RotationData(FloatArray(9){0f})
        return ans
    }
}