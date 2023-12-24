package com.scooter.datacollector.presentation

import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scooter.datacollector.domain.ISessionController
import com.scooter.datacollector.domain.models.AccelerationData
import com.scooter.datacollector.domain.models.Frame
import com.scooter.datacollector.domain.models.GpsData
import com.scooter.datacollector.domain.models.GyroscopeData
import com.scooter.datacollector.domain.usecases.FrameUpdateUsecase
import com.scooter.datacollector.domain.usecases.GetRideSafetyUsecase
import com.scooter.datacollector.domain.usecases.SyncronizeSessionUsecase
import org.koin.java.KoinJavaComponent.inject
import java.util.Date
import java.util.Timer
import kotlin.concurrent.timerTask
import org.koin.java.KoinJavaComponent.get

class MainActivityViewModel() : ViewModel() {
    private val _currentFrame = MutableLiveData<Frame>()
    companion object {
        private const val SAFETY_UPDATE_DELAY_MS = 10000L
    }

    val currentFrame: LiveData<Frame>
        get() = _currentFrame
    private val _rideSafety = MutableLiveData<Double>()
    val rideSafety: LiveData<Double>
        get() = _rideSafety
    private val frameUpdateUsecase : FrameUpdateUsecase by inject(FrameUpdateUsecase::class.java)

    private var timer: Timer? = null
    private lateinit var context: Context

    init {
        frameUpdateUsecase.subscribe {frame -> _currentFrame.value = frame}
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

    fun attachActivity(context: Context){
        this.context = context
    }

    fun sessionStarted(){
        timer = Timer(true)
        val task = timerTask {
            val sessionController: ISessionController = get(ISessionController::class.java)
            val syncSession: SyncronizeSessionUsecase = get(SyncronizeSessionUsecase::class.java)
            syncSession.execute(sessionController.getCurrentSession(), {
                val rideSafetyUsecase: GetRideSafetyUsecase = get(GetRideSafetyUsecase::class.java)
                rideSafetyUsecase.execute({
                    Handler(context.mainLooper).post{
                        _rideSafety.value = it
                    }
                },{})
            },{Log.e("", "error")})
        }
        timer!!.scheduleAtFixedRate(task, SAFETY_UPDATE_DELAY_MS, SAFETY_UPDATE_DELAY_MS)
    }

    fun sessionEnded(){
        timer?.cancel()
        timer = null
    }
}