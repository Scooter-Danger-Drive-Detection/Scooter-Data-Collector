package com.scooter.datacollector.data

import android.util.Log
import com.scooter.datacollector.data.local.LocalDatabase
import com.scooter.datacollector.data.local.entities.FrameEntity
import com.scooter.datacollector.domain.models.Frame
import com.scooter.datacollector.domain.repositories.IFrameRepository

class FrameRepository(private val localDatabase: LocalDatabase) : IFrameRepository {
    override fun saveFrame(frame: Frame) {
        localDatabase.frameDao().insertFrame(FrameEntity(
            frame.frameId,
            frame.sessionId,
            frame.lastFrameId,
            frame.time,
            frame.gps.speed,
            frame.gps.latitude,
            frame.gps.longitude,
            frame.accelerometer.linearAccelerationX,
            frame.accelerometer.linearAccelerationY,
            frame.accelerometer.linearAccelerationZ,
            frame.accelerometer.gravityAccelerationX,
            frame.accelerometer.gravityAccelerationY,
            frame.accelerometer.gravityAccelerationZ,
            frame.gyroscopeData.rotationDelta[0],
            frame.gyroscopeData.rotationDelta[1],
            frame.gyroscopeData.rotationDelta[2],
            frame.gyroscopeData.rotationDelta[3],
            frame.gyroscopeData.rotationDelta[4],
            frame.gyroscopeData.rotationDelta[5],
            frame.gyroscopeData.rotationDelta[6],
            frame.gyroscopeData.rotationDelta[7],
            frame.gyroscopeData.rotationDelta[8],
            frame.gyroscopeData.angleSpeedX,
            frame.gyroscopeData.angleSpeedY,
            frame.gyroscopeData.angleSpeedZ
        ))

        Log.d(FrameRepository::class.java.name, localDatabase.frameDao().getAll().count().toString())
    }
}