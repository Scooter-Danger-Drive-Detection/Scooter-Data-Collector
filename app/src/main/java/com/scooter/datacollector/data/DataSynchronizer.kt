package com.scooter.datacollector.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.scooter.datacollector.data.api.controllers.SessionController
import com.scooter.datacollector.data.api.models.Accelerometer
import com.scooter.datacollector.data.api.models.Frame
import com.scooter.datacollector.data.api.models.Gps
import com.scooter.datacollector.data.api.models.Gyroscope
import com.scooter.datacollector.data.api.models.Session
import com.scooter.datacollector.data.api.requestModels.SaveSessionRequest
import com.scooter.datacollector.data.local.LocalDatabase
import com.scooter.datacollector.domain.IDataSynchronizer
import com.scooter.datacollector.domain.models.RideMode
import java.lang.Exception

class DataSynchronizer(private val context: Context, private val localDatabase: LocalDatabase, private val sessionController: SessionController) :
    IDataSynchronizer {

    override fun synchronizeAll(){
        val allSessions = localDatabase.sessionDao().getAll()
        for (session in allSessions){
            try {
                synchronizeSession(com.scooter.datacollector.domain.models.Session(session.id, session.userId, RideMode.valueOf(session.rideMode.name)))
            }
            catch (e: Exception){
                android.os.Handler(context.mainLooper).post {
                    Toast.makeText(context, "Request error while sync session " + session.id.toString(), Toast.LENGTH_SHORT).show()}
                Log.e(DataSynchronizer::class.java.name, e.message?: "" );
                return
            }
        }
        Log.d(DataSynchronizer::class.java.name, "синхронизация завершена")
    }

    override fun synchronizeSession(session:com.scooter.datacollector.domain.models.Session){
        val framesFound = sessionController.getFramesCountInSession(
            Session(
                session.id,
                session.userId,
                session.rideMode.ordinal
            )
        ).execute().body()!!
        val framesStored = localDatabase.frameDao().getFramesCountBySessionId(session.id)

        Log.d(DataSynchronizer::class.java.name, "на сервере $framesFound, в телефоне $framesStored")
        if(framesFound >= framesStored) return


        val localFrames = localDatabase.frameDao().getFramesBySessionId(session.id)
        val apiFrames = mutableListOf<Frame>()
        for (i in framesFound..<localFrames.size) {
            val frame = localFrames[i]
            apiFrames.add(
                Frame(
                    FrameID = frame.id,
                    SessionID = frame.sessionId,
                    PreviousFrameID = frame.previousFrameId.toLong(),
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
                        RotationDeltaMatrix = listOf(
                            frame.rotationDeltaMatrixField0,
                            frame.rotationDeltaMatrixField1,
                            frame.rotationDeltaMatrixField2,
                            frame.rotationDeltaMatrixField3,
                            frame.rotationDeltaMatrixField4,
                            frame.rotationDeltaMatrixField5,
                            frame.rotationDeltaMatrixField6,
                            frame.rotationDeltaMatrixField7,
                            frame.rotationDeltaMatrixField8
                        ).toFloatArray(),
                        AngleSpeedX = frame.angleSpeedX,
                        AngleSpeedY = frame.angleSpeedY,
                        AngleSpeedZ = frame.angleSpeedZ,
                    )
                )
            )
            if (apiFrames.size >= 100) {
                saveFramesBatch(
                    Session(
                        session.id,
                        session.userId,
                        session.rideMode.ordinal
                    ), apiFrames
                )
                Log.d(DataSynchronizer::class.java.name, "синхронизация сессии " + session.id.toString()+ ", " + frame.id)


                apiFrames.clear()
            }
        }

        saveFramesBatch(
            Session(
                session.id,
                session.userId,
                session.rideMode.ordinal
            ), apiFrames
        )
        Log.d(DataSynchronizer::class.java.name, "синхронизация сессии " + session.id.toString()+ ", " + apiFrames.size)

        apiFrames.clear()
        Log.d(DataSynchronizer::class.java.name, "синхронизация сессии " + session.id.toString()+ ", количество кадров " + localFrames.size + " завершена")
    }

    private fun saveFramesBatch(session: Session, frames: List<Frame>){
        sessionController.saveSession(
            SaveSessionRequest(
                session,
                frames
            )
        ).execute()
    }
}