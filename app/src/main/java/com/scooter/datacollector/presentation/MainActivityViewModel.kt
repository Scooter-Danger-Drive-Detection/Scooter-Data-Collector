package com.scooter.datacollector.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scooter.datacollector.domain.models.AccelerationData
import com.scooter.datacollector.domain.models.Frame
import com.scooter.datacollector.domain.models.GpsData
import com.scooter.datacollector.domain.models.GyroscopeData
import com.scooter.datacollector.domain.usecases.FrameUpdateUsecase
import org.koin.java.KoinJavaComponent.inject
import java.util.Date

class MainActivityViewModel : ViewModel() {
    private val _currentFrame = MutableLiveData<Frame>()
    val currentFrame: LiveData<Frame>
        get() = _currentFrame



    private val frameUpdateUsecase : FrameUpdateUsecase by inject(FrameUpdateUsecase::class.java)

    init {
        frameUpdateUsecase.subscribe {frame -> updateCurrentFrame(frame)}
        _currentFrame.value = Frame(
            frameId = 0,
            sessionId = 0,
            lastFrameId = 0,
            time = Date().time,
            gps = GpsData(speed = 0.0, latitude = 0.0, longitude = 0.0),
            accelerometer = AccelerationData(0.0,0.0,0.0,0.0,0.0,0.0),
            gyroscopeData = GyroscopeData(FloatArray(9),0.0,0.0,0.0)
        )
    }

    private fun updateCurrentFrame(frame: Frame){
        _currentFrame.value = frame
    }

}