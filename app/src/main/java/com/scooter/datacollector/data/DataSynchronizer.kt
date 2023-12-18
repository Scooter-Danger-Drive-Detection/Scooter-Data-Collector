package com.scooter.datacollector.data

import android.content.Context
import android.widget.Toast
import com.scooter.datacollector.data.api.controllers.SessionController
import com.scooter.datacollector.data.api.models.Accelerometer
import com.scooter.datacollector.data.api.models.Frame
import com.scooter.datacollector.data.api.models.Gps
import com.scooter.datacollector.data.api.models.Gyroscope
import com.scooter.datacollector.data.api.models.Id
import com.scooter.datacollector.data.api.models.RideMode
import com.scooter.datacollector.data.api.models.Session
import com.scooter.datacollector.data.api.requestModels.GetFramesCountInSessionRequest
import com.scooter.datacollector.data.api.requestModels.SaveSessionRequest
import com.scooter.datacollector.data.local.LocalDatabase
import com.scooter.datacollector.data.local.entities.SessionEntity
import java.lang.Exception
import java.util.logging.Handler

class DataSynchronizer(private val context: Context, private val localDatabase: LocalDatabase, private val sessionController: SessionController) {

    fun trySynchronizeAll(){
        val allSessions = localDatabase.sessionDao().getAll()
        for (session in allSessions){
            run{
                try {
                    trySynchronizeSession(session)
                }
                catch (e: Exception){
                    android.os.Handler(context.mainLooper).post {
                        Toast.makeText(context, "Request error while sync session " + session.id.toString(), Toast.LENGTH_SHORT).show()}
                }
            }
        }
    }

    private fun trySynchronizeSession(session:SessionEntity){
        val framesFound = sessionController.getFramesCountInSession(
            GetFramesCountInSessionRequest(
                Session(
                    session.id,
                    session.userId,
                    RideMode.valueOf(session.rideMode.name)
                )
            )
        ).execute().body()!!.framesCount
        val framesStored = localDatabase.frameDao().getFramesCountBySessionId(session.id)

        if(framesFound >= framesStored) return

        val localFrames = localDatabase.frameDao().getFramesBySessionId(session.id)
        val apiFrames = mutableListOf<Frame>()
        for (frame in localFrames){
            apiFrames.add(
                Frame(
                    Id(
                        FrameId = frame.id,
                        SessionId = frame.sessionId,
                        LastFrameId = frame.previousFrameId.toLong(),
                    ),
                    Time = frame.time,
                    GPS = Gps(
                        Speed = frame.speed,
                        Longitude = frame.longitude,
                        Latitude = frame.latitude,
                    ),
                    Accelerometer = Accelerometer(
                        AccelerationX = frame.linearAccelerationX,
                        AccelerationY = frame.linearAccelerationY,
                        AccelerationZ = frame.linearAccelerationZ,
                        GravityX = frame.gravityAccelerationX,
                        GravityY = frame.gravityAccelerationY,
                        GravityZ = frame.gravityAccelerationZ,
                    ),
                    Gyroscope = Gyroscope(
                        RotationDeltaMatrix = listOf(frame.rotationDeltaMatrixField0, frame.rotationDeltaMatrixField1, frame.rotationDeltaMatrixField2, frame.rotationDeltaMatrixField3, frame.rotationDeltaMatrixField4, frame.rotationDeltaMatrixField5, frame.rotationDeltaMatrixField6, frame.rotationDeltaMatrixField7, frame.rotationDeltaMatrixField8).toFloatArray(),
                        AngleSpeedX = frame.angleSpeedX,
                        AngleSpeedY = frame.angleSpeedY,
                        AngleSpeedZ = frame.angleSpeedZ,
                    )
                )
            )
        }
        sessionController.saveSession(
            SaveSessionRequest(
                Session(
                    session.id,
                    session.userId,
                    RideMode.valueOf(session.rideMode.name)
                ),
                apiFrames
            )
        )
        android.os.Handler(context.mainLooper).post{
            Toast.makeText(context, "синхронизация сессии " + session.id.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}