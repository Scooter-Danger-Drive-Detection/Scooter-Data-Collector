package com.scooter.datacollector.domain

import com.scooter.datacollector.domain.models.Frame
import com.scooter.datacollector.domain.repositories.IFrameRepository
import com.scooter.datacollector.domain.sensors.IAccelerometer
import com.scooter.datacollector.domain.sensors.IGps
import com.scooter.datacollector.domain.sensors.IGyroscope
import java.util.Date
import java.util.Timer
import kotlin.concurrent.timerTask
import kotlin.collections.MutableList

class FrameDataCollector(
    private val accelerometer: IAccelerometer,
    private val gps: IGps,
    private val gyroscope: IGyroscope,
    private val sessionController: ISessionController,
    private val frameRepository: IFrameRepository
) : IFrameDataCollector {
    companion object{
        const val DELAY_BETWEEN_FRAMES_MS: Long = 50L
    }

    private var timer: Timer? = null
    private var currentFrame: Int = 0
    private val frameUpdatedCallbacks: MutableList<(Frame) -> Unit> = mutableListOf()
    override fun startFrameDataCollection(){
        currentFrame = 0

        timer = Timer(true)
        val task = timerTask {
            collectFrameData()
        }
        timer!!.scheduleAtFixedRate(task, 0, DELAY_BETWEEN_FRAMES_MS)
    }

    private fun collectFrameData(){
        val frame: Frame = Frame(
            frameId = currentFrame,
            sessionId = sessionController.getCurrentSession().id,
            lastFrameId = currentFrame - 1,
            time = Date().time,
            gps = gps.getGpsData(),
            accelerometer = accelerometer.getAccelerationData(),
            gyroscopeData = gyroscope.getRotationData()
        )
        frameRepository.saveFrame(frame)
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