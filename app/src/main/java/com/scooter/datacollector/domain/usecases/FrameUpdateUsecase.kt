package com.scooter.datacollector.domain.usecases

import com.scooter.datacollector.domain.IFrameDataCollector
import com.scooter.datacollector.domain.models.Frame

class FrameUpdateUsecase(private val frameDataCollector: IFrameDataCollector) {
    public fun subscribe(action : (Frame) -> Unit){
        frameDataCollector.subscribeOnFrameUpdate(action)
    }

    public fun unsubscribe(action : (Frame) -> Unit){
        frameDataCollector.unsubscribeOnFrameUpdate(action)
    }
}