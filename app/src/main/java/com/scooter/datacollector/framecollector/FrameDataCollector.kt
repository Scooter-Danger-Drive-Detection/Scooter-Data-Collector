package com.scooter.datacollector.framecollector

import android.content.Context
import com.scooter.datacollector.domain.IFrameDataCollector
import com.scooter.datacollector.domain.ISessionController
import com.scooter.datacollector.domain.models.Frame
import com.scooter.datacollector.domain.repositories.IFrameRepository
import com.scooter.datacollector.domain.sensors.IAccelerometer
import com.scooter.datacollector.domain.sensors.IGps
import com.scooter.datacollector.domain.sensors.IGyroscope
import java.util.Date
import java.util.Timer
import java.util.logging.Handler
import kotlin.concurrent.timerTask
import kotlin.collections.MutableList

class FrameDataCollector(
    private val context: Context,
    private val accelerometer: IAccelerometer,
    private val gps: IGps,
    private val gyroscope: IGyroscope,
    private val frameRepository: IFrameRepository
) : IFrameDataCollector {
    companion object{
        const val DELAY_BETWEEN_FRAMES_MS: Long = 100L
    }

    private var timer: Timer? = null
    private var currentFrame: Int = 0
    private var currentSessionId = 0
    private val frameUpdatedCallbacks: MutableList<(Frame) -> Unit> = mutableListOf()
    override fun startFrameDataCollection(sessionId: Int){
        currentFrame = 0
        currentSessionId = sessionId

        timer = Timer(true)
        val task = timerTask {
            collectFrameData()
        }
        timer!!.scheduleAtFixedRate(task, 0, DELAY_BETWEEN_FRAMES_MS)
    }

    private fun collectFrameData(){
        val frame: Frame = Frame(
            frameId = currentFrame,
            sessionId = currentSessionId,
            lastFrameId = currentFrame - 1,
            time = Date().time,
            gps = gps.getGpsData(),
            accelerometer = accelerometer.getAccelerationData(),
            gyroscopeData = gyroscope.getRotationData()
        )
        frameRepository.saveFrame(frame)

        currentFrame++
        for(callback in frameUpdatedCallbacks){
            android.os.Handler(context.mainLooper).post{
                callback(frame)
            }
        }
    }

    override fun stopFrameDataCollection(){
        timer?.cancel()
        timer = null
    }

    override fun subscribeOnFrameUpdate(callback: (Frame) -> Unit) {
        frameUpdatedCallbacks.add(callback)
    }

    override fun unsubscribeOnFrameUpdate(callback: (Frame) -> Unit) {
        val callbackPosition = frameUpdatedCallbacks.indexOf(callback)
        if(callbackPosition == -1) return
        val temp: (Frame) -> Unit = frameUpdatedCallbacks[callbackPosition]
        frameUpdatedCallbacks[callbackPosition] = frameUpdatedCallbacks.last()
        frameUpdatedCallbacks[frameUpdatedCallbacks.lastIndex] = temp
        frameUpdatedCallbacks.removeLast()
    }
}