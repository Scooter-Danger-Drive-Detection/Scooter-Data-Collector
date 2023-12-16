package com.scooter.datacollector.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scooter.datacollector.domain.models.Frame
import com.scooter.datacollector.domain.usecases.FrameUpdateUsecase
import org.koin.java.KoinJavaComponent.inject

class MainActivityViewModel : ViewModel() {
    private val _currentFrame = MutableLiveData<Frame>()
    val currentFrame: LiveData<Frame>
        get() = _currentFrame



    private val frameUpdateUsecase : FrameUpdateUsecase by inject(FrameUpdateUsecase::class.java)

    init {
        //frameUpdateUsecase.subscribe {frame -> updateCurrentFrame(frame)}
    }

    private fun updateCurrentFrame(frame: Frame){
        _currentFrame.value = frame
    }

}